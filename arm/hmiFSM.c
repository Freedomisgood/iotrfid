
#include <iostream>

using namespace std;

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <unistd.h>

#include "cQueue.h"
#include "myQueue.h"
#include "myMQTT.h"
#include "serialscreen.h"
#include "hmiFSM.h"
#include "cJSON.h"
#include "camera.h"
#include "sscreenupdate.h"
#include "base64.h"
#include "faceSearch.h"

char *statuslist[]={"C9FAB2FAD6D0","D2D1D4DDCDA3","CEB4C9FAB2FA","D2D1BDE1CAF8"};
char *alarmlist[]={"red","yellow"};

extern Queue 		* qrfid;		
extern Queue 		* qscreensend;
extern Queue 		* qscreenrecv;
extern CQueue 		* qmqttsend;
extern CQueue 		* qmqttrecv;
extern CQueue 		* qprintrecv;

extern pthread_mutex_t mutex_rfidqueue;		
extern pthread_mutex_t mutex_scnsendqueue;
extern pthread_mutex_t mutex_scnrecvqueue;
extern pthread_mutex_t mutex_mqttrecvqueue;
extern pthread_mutex_t mutex_mqttsendqueue;
extern pthread_mutex_t mutex_printrecvqueue;

int getScreenID(char *cmd);
int getUser(char *cmd,char *user);
int getPass(char *cmd,char *pass);
int getOrderRecordID(char *cmd);

#define SIZE_PIC		2*1024*1024	/*1M Bytes*/
#define SIZE_CMD		1*1024		/*1K Bytes*/
unsigned char	pic[SIZE_PIC];
unsigned char	base64[SIZE_PIC];
extern char mac[];
cJSON *rootface;
char temp[1024]={0};
char tempuser[1024]={0};

void FSM_init(struct MQTTReturn *mr,struct CurState cs[5] )
{
	memcpy(mr->ud.userAccount,"123456",6);
	memcpy(mr->ud.realName,"张三",4);
	memcpy(mr->ud.employId,"654321",6);

	memcpy(mr->rfid,"7593879C",8);

	mr->login	= NOTLOGIN;
	mr->page	= PAGE_START;

	cs[STATE_RFID].screenid		= STATE_RFID;
	cs[STATE_USER].screenid		= STATE_USER;
	cs[STATE_MAIN].screenid		= STATE_MAIN;
	cs[STATE_DETAIL].screenid	= STATE_DETAIL;

	cs[STATE_RFID].recordid		= RDEMPTY;
	cs[STATE_USER].recordid		= RDEMPTY;
	cs[STATE_MAIN].recordid		= RDEMPTY;
	cs[STATE_DETAIL].recordid	= RDEMPTY;

	cs[STATE_RFID].login= NOTLOGIN;
	cs[STATE_USER].login= NOTLOGIN;
	cs[STATE_MAIN].login= NOTLOGIN;
	cs[STATE_DETAIL].login= NOTLOGIN;

	memset(cs[STATE_USER].user,0,SIZE_USER);
	memset(cs[STATE_USER].pass,0,SIZE_PASS);
	memset(cs[STATE_RFID].rfid,0,SIZE_RFID);

}
void *th_hmiFSM()
{
	int scnid		=-1,wdgid=-1,btnup;
	int				iv=0,page;
	char			*cmd;
	char			msg[SIZE_MQTT_MSG];
	char			user[64];
	char			pass[64];
	char			dtnum[64];
	char			require[SIZE_TEMP];

	struct CurState cs[16];
	struct MQTTReturn mr;

	/* intermediate data */
	QNode	front;
	CQNode	cfront;
	struct	ComFrame cf;	
	Itemt1	itm;


	FSM_init(&mr,cs);

	while(true)
	{
		/* handle the data from rfid */
		if( !IsEmptyQueue(qrfid))
		{
			printf("rfid\n");
			front		= qrfid->front;
			cf.frmlen	= front->data->frmlen;
			memcpy(cf.data,front->data->data,front->data->frmlen);

			pthread_mutex_lock(&mutex_rfidqueue);
			DeQueue(qrfid);
			pthread_mutex_unlock(&mutex_rfidqueue);

			memset(mr.rfid,0,SIZE_RFID);
			memcpy(mr.rfid,cf.data,cf.frmlen);

			if(mr.login == LOGIN)
			{
				printf("you have logined, repeat operation is not needed\n");
				// mqtt_send_cmd(RFIDLOGIN,mac,NULL,NULL,NULL,mr.rfid,NULL,0);
			}
			else if(mr.login == NOTLOGIN)
			{
				mqtt_send_cmd(RFIDLOGIN,mac,NULL,NULL,NULL,mr.rfid,NULL,0);
			}
		}

		/* handle the data from mqtt */
		if( !IsEmptyCQueue(qmqttrecv))
		{
			memset(msg,0,SIZE_MQTT_MSG);

			cfront = qmqttrecv->front;
			memcpy(msg,cfront->data,strlen(cfront->data));

			pthread_mutex_lock(&mutex_mqttrecvqueue);
			DeCQueue(qmqttrecv);
			pthread_mutex_unlock(&mutex_mqttrecvqueue);

			pthread_mutex_lock(&mutex_mqttrecvqueue);
			get_jsondata(&mr,msg);
			printf("%s\n",msg);
			pthread_mutex_unlock(&mutex_mqttrecvqueue);

			switch( mr.function )
			{
				case RFIDLOGIN:
					if(mr.status == 0)
					{
						/* login failed*/
						mr.login = NOTLOGIN;
						printf("rfid login failed!\n");
					}
					else if( cs[STATE_USER].login == NOTLOGIN )
					{
						/* login success */
						mr.login = LOGIN;
						printf("rfid login success!\n");
					}
					break;
				default:
					break;
			}
		}
		//avoid too much cpu occupation;
		usleep(10*1000);
	}// end while
}

