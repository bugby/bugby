package org.bugby.wildcard;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;

import org.richast.node.ASTNodeData;

public class NodeUtils {
	public static Statement getNextStatement(Node node) {
		Statement stmt = ASTNodeData.parent(node, Statement.class);
		Node parent = ASTNodeData.parent(stmt);
		if (parent instanceof BlockStmt) {
			BlockStmt block = (BlockStmt) parent;
			int index = block.getStmts().indexOf(stmt);
			if (index < block.getStmts().size() - 1) {
				return block.getStmts().get(index + 1);
			}
		}
		return null;
	}
}
