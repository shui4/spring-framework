package indi.shui4.thinking.spring.bean.scope;

import indi.shui4.thinking.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


/**
 * 自定义 Scope {@link ThreadLocalScope} 示例
 *
 * @author shui4
 */
public class ThreadLocalScopeDemo {

	private static User createUser() {
		User user = new User();
		user.setId(System.nanoTime());
		return user;
	}

	public static void main(String[] args) {

		// 创建 BeanFactory 容器
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		// 注册 Configuration Class（配置类） -> Spring Bean
		applicationContext.register(ThreadLocalScopeDemo.class);
		// 注册自定义 scope
		applicationContext.addBeanFactoryPostProcessor(beanFactory -> beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope()));

		// 启动 Spring 应用上下文
		applicationContext.refresh();

		scopedBeansByLookup(applicationContext);

		// 关闭 Spring 应用上下文
		applicationContext.close();
	}

	private static void scopedBeansByLookup(AnnotationConfigApplicationContext applicationContext) {

		for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(() -> {
				// user 是共享 Bean 对象
				User user = applicationContext.getBean("user", User.class);
				System.out.printf("[Thread id :%d] user = %s%n", Thread.currentThread().getId(), user);
			});

			// 启动线程
			thread.start();
			// 强制线程执行完成
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	@SuppressWarnings({"squid:S1144", "unused"})
	private static void scopedBeansByInjection(AnnotationConfigApplicationContext applicationContext) {
		throw new UnsupportedOperationException();
	}

	@Bean
	@Scope(ThreadLocalScope.SCOPE_NAME)
	public User user() {
		return createUser();
	}
}
