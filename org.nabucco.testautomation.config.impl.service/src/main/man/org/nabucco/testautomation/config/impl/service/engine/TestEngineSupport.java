/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.config.impl.service.engine;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.engine.TestEngine;
import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.base.net.TestEngineConnectionFactory;
import org.nabucco.testautomation.facade.component.TestautomationComponent;
import org.nabucco.testautomation.facade.component.TestautomationComponentLocator;
import org.nabucco.testautomation.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.facade.datatype.engine.proxy.ProxyConfiguration;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;
import org.nabucco.testautomation.facade.message.TestEngineConfigurationMsg;
import org.nabucco.testautomation.facade.message.TestEngineConfigurationSearchMsg;
import org.nabucco.testautomation.facade.service.search.SearchTestEngineConfiguration;

/**
 * TestEngineSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestEngineSupport {

	public static final String N_A = "n/a";

	private static final NabuccoLogger logger = NabuccoLoggingFactory
			.getInstance().getLogger(TestEngineSupport.class);

	private static SearchTestEngineConfiguration configSearch;

	public static TestEngine getTestEngine(TestEngineConfiguration config)
			throws TestEngineException {

		ConnectionSpec connectionSpec = new ConnectionSpec(config.getHost()
				.getValue(), config.getPort().getValue(), config
				.getRemoteReferenceName().getValue());

		try {
			TestEngine engine = TestEngineConnectionPool.getInstance().get(
					connectionSpec);

			if (engine == null) {
				logger.info("Looking up TestEngine using "
						+ connectionSpec.toString());
				Registry registry = LocateRegistry.getRegistry(
						connectionSpec.getHost(), connectionSpec.getPort(),
						new TestEngineConnectionFactory());
				engine = (TestEngine) registry.lookup(connectionSpec
						.getRemoteName());

				if (engine == null) {
					throw new TestEngineException("No TestEngine found using "
							+ connectionSpec);
				}
				TestEngineConnectionPool.getInstance().put(connectionSpec,
						engine);
				logger.info("Lookup of TestEngine successful");
			}

			return engine;
		} catch (AccessException ex) {
			throw new TestEngineException(ex);
		} catch (RemoteException ex) {
			throw new TestEngineException(ex);
		} catch (NotBoundException ex) {
			throw new TestEngineException(ex);
		}
	}
	
	public static void returnTestEngine(TestEngineConfiguration config)
			throws TestEngineException {

		ConnectionSpec connectionSpec = new ConnectionSpec(config.getHost()
				.getValue(), config.getPort().getValue(), config
				.getRemoteReferenceName().getValue());

		TestEngine engine = TestEngineConnectionPool.getInstance().remove(
				connectionSpec);

		if (engine != null) {
			logger.info("TestEngine returned (" + connectionSpec.toString()
					+ ")");
		}
	}

	public static TestEngineConfiguration getTestEngineConfiguration(
			Code release, Code environment, ServiceMessageContext ctx, User user)
			throws TestEngineException {

		if (configSearch == null) {
			try {
				TestautomationComponent testautomation = TestautomationComponentLocator
						.getInstance().getComponent();
				configSearch = testautomation
						.getSearchTestEngineConfiguration();
			} catch (Exception ex) {
				throw new TestEngineException(
						"Could not initilize SearchTestEngineConfiguration", ex);
			}
		}

		try {
			ServiceRequest<TestEngineConfigurationSearchMsg> rq = new ServiceRequest<TestEngineConfigurationSearchMsg>(
					ctx);
			TestEngineConfigurationSearchMsg msg = new TestEngineConfigurationSearchMsg();
			msg.setEnvironmentType(environment);
			msg.setReleaseType(release);
			msg.setUser(user);
			rq.setRequestMessage(msg);

			TestEngineConfiguration config = null;

			try {
				TestEngineConfigurationMsg rs = configSearch
						.getTestEngineConfiguration(rq).getResponseMessage();
				config = rs.getTestEngineConfiguration();
			} catch (SearchException ex) {
				logger.warning("Could not find TestEngineConfiguration for current user");
			}

			// Get first SearchResult
			if (config == null) {
				List<TestEngineConfiguration> searchResults = configSearch
						.searchTestEngineConfiguration(rq).getResponseMessage()
						.getConfigurationList();

				if (searchResults.isEmpty()) {
					return createDefaultConfiguration();
				}

				TestEngineConfigurationSearchMsg msg2 = new TestEngineConfigurationSearchMsg();
				msg2.setId(new Identifier(searchResults.get(0).getId()));
				rq.setRequestMessage(msg2);
				return configSearch.getTestEngineConfiguration(rq)
						.getResponseMessage().getTestEngineConfiguration();
			} else {
				return config;
			}
		} catch (Exception e) {
			throw new TestEngineException(
					"Could not get TestEngineConfiguration: " + e.getMessage());
		}
	}

	private static TestEngineConfiguration createDefaultConfiguration() {
		TestEngineConfiguration testEngineConfiguration = new TestEngineConfiguration();
		testEngineConfiguration.setHost("localhost");
		testEngineConfiguration.setPort("1099");
		testEngineConfiguration.setName("Default TestEngineConfiguration");
		testEngineConfiguration.setRemoteReferenceName("TestEngine");
		return testEngineConfiguration;
	}

	public static void validateTestEngineConfigurationConfiguration(
			TestEngineConfiguration config) throws TestEngineException {

		if (config == null) {
			throw new TestEngineException("No TestEngineConfiguration supplied");
		}

		if (config.getHost() == null || config.getHost().getValue() == null) {
			throw new TestEngineException("No hostname configured");
		}

		if (config.getPort() == null || config.getPort().getValue() == null) {
			throw new TestEngineException("No port configured");
		}

		if (config.getRemoteReferenceName() == null
				|| config.getRemoteReferenceName().getValue() == null) {
			throw new TestEngineException("No remoteReferenceName configured");
		}
	}

	public static TestContext createTestContext(TestEngineConfiguration config,
			User user, Code release, Code environment) {

		TestContext ctx = new TestContext();

		// Add ProxyConfigurations
		for (ProxyConfiguration proxyConfig : config.getProxyConfigurations()) {
			ctx.addProxyConfiguration(proxyConfig);
		}

		if (user != null && user.getUsername() != null) {
			StringProperty userProp = new StringProperty();
			userProp.setName(TestContext.USERNAME);
			userProp.setValue(user.getUsername().getValue());
			ctx.put(userProp);
		} else {
			StringProperty userProp = new StringProperty();
			userProp.setName(TestContext.USERNAME);
			userProp.setValue("Anonymous");
			ctx.put(userProp);
		}

		StringProperty emailProp = new StringProperty();
		emailProp.setName(TestContext.EMAIL);
		emailProp.setValue("no.reply@prodyna.de");
		ctx.put(emailProp);

		if (release != null && release.getName() != null) {
			StringProperty releaseProp = new StringProperty();
			releaseProp.setName(TestContext.RELEASE);
			releaseProp.setValue(release.getName().getValue());
			ctx.put(releaseProp);
		}

		if (environment != null && environment.getName() != null) {
			StringProperty envProp = new StringProperty();
			envProp.setName(TestContext.ENVIRONMENT);
			envProp.setValue(environment.getName().getValue());
			ctx.put(envProp);
		}

		return ctx;
	}

}
