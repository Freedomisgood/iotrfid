package top.nymrli.iotrfid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @description:
 * @author: MrLi
 * @create: 2021-09-27 17:34
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfiguartion {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("DAF API Doc")
//                .description("This is a restful api document of DAF.")
//                .version("1.0")
//                .build();
//    }
}
