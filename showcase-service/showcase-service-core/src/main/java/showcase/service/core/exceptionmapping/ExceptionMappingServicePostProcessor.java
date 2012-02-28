package showcase.service.core.exceptionmapping;

import javax.annotation.PostConstruct;

import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ExceptionMappingServicePostProcessor extends ProxyConfig implements BeanPostProcessor, Ordered {

    private Advisor advisor;

    @Autowired
    private ExceptionMappingInterceptor interceptor;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AopInfrastructureBean) {
            // Ignore AOP infrastructure such as scoped proxies.
            return bean;
        }
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        if (AopUtils.canApply(advisor, targetClass)) {
            if (bean instanceof Advised) {
                ((Advised) bean).addAdvisor(0, advisor);
                return bean;
            } else {
                ProxyFactory proxyFactory = new ProxyFactory(bean);
                // Copy our properties (proxyTargetClass etc) inherited from ProxyConfig.
                proxyFactory.copyFrom(this);
                proxyFactory.addAdvisor(advisor);
                return proxyFactory.getProxy();
            }
        } else {
            return bean;
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @PostConstruct
    private void init() {
        Pointcut pointcut = new AnnotationMatchingPointcut(Service.class, true);
        advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
