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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.testautomation.engine.TestEngine;


/**
 * TestEngineConnectionPool
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestEngineConnectionPool {

	private static TestEngineConnectionPool instance;
	
	private Map<ConnectionSpec, TestEngine> enginePool;
	
	private TestEngineConnectionPool() {
		this.enginePool = new HashMap<ConnectionSpec, TestEngine>();
	}
	
	/**
	 * 
	 * @return
	 */
	public static TestEngineConnectionPool getInstance() {
		
		if (instance == null) {
			instance = new TestEngineConnectionPool();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param connection
	 * @param testEngine
	 */
	public void put(ConnectionSpec connection, TestEngine testEngine) {
		this.enginePool.put(connection, testEngine);
	}
	
	/**
	 * 
	 * @param connection
	 * @return
	 */
	public TestEngine get(ConnectionSpec connection) {
		return this.enginePool.get(connection);
	}
	
	/**
	 * 
	 * @param connection
	 * @return
	 */
	public TestEngine remove(ConnectionSpec connection) {
		return this.enginePool.remove(connection);
	}
	
}
