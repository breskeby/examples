package com.breskeby.example

import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.FieldNode
import java.lang.reflect.Modifier
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ReturnStatement

/**
 * Created by IntelliJ IDEA.
 * User: Rene
 * Date: 10.06.2010
 * Time: 22:51:51
 * To change this template use File | Settings | File Templates.
 */
@GroovyASTTransformation(phase = CompilePhase.INSTRUCTION_SELECTION)
class CachedTransformation implements ASTTransformation {

  void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
    if(!astNodes) return
    if(!astNodes[0]) return
    if(!astNodes[1]) return
    if(!(astNodes[0] instanceof AnnotationNode)) return
    if(!(astNodes[1] instanceof MethodNode)) return

    //validate AnnotationNode
    MethodNode annotatedMethod = astNodes[1]
    if(annotatedMethod.parameters.length != 1) return
    if(annotatedMethod.returnType.name == "void") return

    ClassNode declaringClass = annotatedMethod.declaringClass
    makeMethodCached(declaringClass, annotatedMethod)
  }

  void makeMethodCached(ClassNode classNode, MethodNode methodNode) {
   // add field of hashmap for cached objects
   def cachedFieldName = methodNode.getName();
   FieldNode cachedField =
    new FieldNode("cache$cachedFieldName", Modifier.PRIVATE, new ClassNode(Map.class), new ClassNode(classNode.getClass()),
      new ConstructorCallExpression(new ClassNode(HashMap.class), new ArgumentListExpression()));
    classNode.addField(cachedField)

    //augment method with cache calls

    Parameter[] params = methodNode.getParameters()
    //methodNode
    String parameterName = params[0].getName()
    List<Statement> statements = methodNode.getCode().getStatements();


    Statement oldReturnStatement = statements.last();
    def ex = oldReturnStatement.getExpression();
    def ast = new AstBuilder().buildFromSpec  {
      expression{
          declaration {
                variable "cachedValue"
                token "="
                methodCall {
                    variable "cache$cachedFieldName"
                    constant 'get'
                    argumentList {
                      variable parameterName
                    }
                }
          }
      }
      ifStatement {
          booleanExpression {
              variable "cachedValue"
          }
          //if block
          returnStatement {
              variable "cachedValue"
          }
          //else block
          empty()
      }
      expression{
          declaration {
            variable "localCalculated$cachedFieldName"
            token "="
            {-> delegate.expression << ex}()
          }
        }
        expression {
          methodCall {
            variable "cache$cachedFieldName"
            constant 'put'
            argumentList {
              variable parameterName
              variable "localCalculated$cachedFieldName"
            }
          }
        }
        returnStatement {
              variable "localCalculated$cachedFieldName"
        }
    }

    statements.remove(oldReturnStatement)
    statements.add(0,ast[0]);
    statements.add(1,ast[1]);
    statements.add(ast[2])
    statements.add(ast[3])
    statements.add(ast[4])
  }
}
