package cholog.scan;

import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
/*
ComponentScan에 대해 학습하고, ComponenetScanBean을 Bean으로 등록하기
 */
@ComponentScan(basePackages = "cholog.scan")
public class ContextConfiguration {
}
