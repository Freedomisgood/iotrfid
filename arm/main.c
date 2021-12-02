#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <signal.h>

#include "mosquitto.h"
#include "cJSON.h"
#include "sqlite3.h"
#include "print.h"

#include "serialscreen.h"
#include "rfid.h"
#include "info.h"
#include "mythread.h"
#include "myMQTT.h"
#include "myUart.h"
#include "myQueue.h"
#include "cQueue.h"
#include "hmiFSM.h"

extern int	gateway_id;
extern char factory_name[128];
extern char database_path[128];

extern int	UART232ID;
extern int	UART485ID;

extern int	UART_PTR_ID;
extern int	UART_SCN_ID;
extern int	UART_RFID_ID;
extern int	UART_CAMERA_ID;

extern char topic_send[256];
extern char topic_serialscreen[256];
int			gatewayid;
char		mac[64];

Queue 		* qrfid;			//发送命令队列
Queue 		* qscreensend;		//发送命令队列
Queue 		* qscreenrecv;		//发送命令队列

CQueue 		* qmqttsend;		//发送命令队列
CQueue 		* qmqttrecv;		//发送命令队列

CQueue 		* qprintrecv;		//发送命令队列

pthread_mutex_t mutex_rfidqueue;		//发送队列资源锁
pthread_mutex_t mutex_scnsendqueue;		//发送队列资源锁
pthread_mutex_t mutex_scnrecvqueue;		//发送队列资源锁
pthread_mutex_t mutex_mqttrecvqueue;		//发送队列资源锁
pthread_mutex_t mutex_mqttsendqueue;		//发送队列资源锁
pthread_mutex_t mutex_printrecvqueue;		//发送队列资源锁


void sysinit()
{

	printf("----成功获取连接信息----\n");
	UART_RFID_ID	=	uartInit9600(UART_RFID_DIR);

	qrfid		=	InitQueue();

	qmqttsend	= 	InitCQueue();
	qmqttrecv	= 	InitCQueue();

	pthread_mutex_init(&mutex_rfidqueue, NULL);
	pthread_mutex_init(&mutex_mqttsendqueue, NULL);
	pthread_mutex_init(&mutex_mqttrecvqueue, NULL);

}
int main(int argc, char **argv)
{
	int err;
	pthread_t	th_print_id;
	pthread_t	th_request_print_senduart_id;
	pthread_t	th_request_print_recvuart_id;

	/* screen recv&send  pthread*/
	pthread_t	th_rfid_recv_id;
	/* screen recv&send  pthread*/
	pthread_t	th_serialscreen_send_id;
	pthread_t	th_serialscreen_recv_id;
	/* mqtt recv&send  pthread*/
	pthread_t	th_mqtt_send_id;
	pthread_t	th_mqtt_recv_id;

	/* FSM pthread*/
	pthread_t	th_hmiFSM_id;

	sysinit();


	/* mqtt send thread */
	err = pthread_create(&th_mqtt_send_id, NULL, (void*) th_mqtt_send, NULL);
	if(err != 0)
	{
		printf("can't create th_recv_id: %s\n",strerror(err));
		exit(-1);
	}

	/* mqtt receive thread */
	err = pthread_create(&th_mqtt_recv_id, NULL, (void*) th_mqtt_recv, NULL);
	if(err != 0)
	{
		printf("can't create th_recv_id: %s\n",strerror(err));
		exit(-1);
	}

	/* receive rfid command */
	err = pthread_create(&th_rfid_recv_id, NULL, (void*) th_rfid_recv, NULL);
	if(err != 0)
	{
		printf("can't create th_rfid_id: %s\n",strerror(err));
		exit(-1);
	}


	/* HMI FSM */
	err = pthread_create(&th_hmiFSM_id, NULL, (void*) th_hmiFSM, NULL);
	if(err != 0)
	{
		printf("can't create th_hmiFSM: %s\n",strerror(err));
		exit(-1);
	}

	pthread_join(th_mqtt_send_id, NULL);
	pthread_join(th_mqtt_recv_id, NULL);
	pthread_join(th_hmiFSM_id, NULL);

	pthread_mutex_destroy(&mutex_rfidqueue);
	pthread_mutex_destroy(&mutex_mqttsendqueue);
	pthread_mutex_destroy(&mutex_mqttrecvqueue);

	return 0;
}
