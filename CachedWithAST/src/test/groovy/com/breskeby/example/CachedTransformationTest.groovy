package com.breskeby.example

import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TranformTestHelper

/**
 * Created by IntelliJ IDEA.
 * User: Rene
 * Date: 10.06.2010
 * Time: 22:56:32
 * To change this template use File | Settings | File Templates.
 */
class CachedTransformationTest extends GroovyTestCase {

    def cut;
    public void setUp(){
        def file = new File('./CachedExample.groovy')
        assert file.exists()
        def invoker = new TranformTestHelper(new CachedTransformation(), CompilePhase.CANONICALIZATION)
        def clazz = invoker.parse(file)
        cut = clazz.newInstance()
    }

    public void testCalculateWithReturnLocalVariable() {
       def ret1 =  cut.calculateWithReturnLocalVariable("test")
       def ret2 =  cut.calculateWithReturnLocalVariable("test")
       assert ret1 == ret2
    }

    public void testCalculateWithReturnStatement() {
       def ret1 =  cut.calculateWithReturnStatement("test")
       def ret2 =  cut.calculateWithReturnStatement("test")
       assert ret1 == ret2
    }

    public void testCalculateWithoutExplicitReturnStatement() {
       def ret1 =  cut.calculateWithoutExplicitReturnStatement("test")
       def ret2 =  cut.calculateWithoutExplicitReturnStatement("test")
       assert ret1 == ret2
    }

    public void testCalculateWithoutExplicitReturnLocalVariable() {
       def ret1 =  cut.calculateWithoutExplicitReturnLocalVariable("test")
       def ret2 =  cut.calculateWithoutExplicitReturnLocalVariable("test")
       assert ret1 == ret2
    }
}
