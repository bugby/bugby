/**
 *  Copyright 2011 Alexandru Craciun, Eyal Kaspi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.richast.visitor;

import japa.parser.ast.Node;

import org.richast.GenerationContext;
import org.richast.node.ASTNodeData;

/**
 * This visitor go to every node and sets it's parent
 * 
 * @author acraciun
 * 
 */
public class SetParentVisitor extends ForEachNodeVisitor<GenerationContext> {
	private Node currentParent;

	@Override
	protected void before(Node node, GenerationContext arg) {
		node.setData(new ASTNodeData(currentParent));
		currentParent = node;
	}

	@Override
	protected void after(Node node, GenerationContext arg) {
		currentParent = ((ASTNodeData) node.getData()).getParent();
	}
}
