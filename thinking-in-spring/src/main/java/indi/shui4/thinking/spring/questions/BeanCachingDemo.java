/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package indi.shui4.thinking.spring.questions;

import indi.shui4.thinking.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * Bean 是否缓存示例
 *
 * @author shui4
 * @see ObjectFactory
 * @see ObjectProvider
 */
public class BeanCachingDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 注册 Configuration Class
		context.register(BeanCachingDemo.class);

		// 启动 Spring 应用上下文
		context.refresh();

		// BeanCachingDemo 是 Configuration Class，Singleton Scope Bean
		BeanCachingDemo beanCachingDemo = context.getBean(BeanCachingDemo.class);

		for (int i = 0; i < 9; i++) {
			// Singleton Scope Bean 是存在缓存
			System.out.println(beanCachingDemo == context.getBean(BeanCachingDemo.class));
		}

		User user = context.getBean(User.class);

		for (int i = 0; i < 9; i++) {
			// Prototype Scope Bean
			System.out.println(user == context.getBean(User.class));
		}

		// 关闭 Spring 应用上下文
		context.close();
	}

	@Bean
	@Scope("prototype") // 原型 scope
	public static User user() {
		User user = new User();
		user.setId(1L);
		user.setName("小马哥");
		return user;
	}
}
