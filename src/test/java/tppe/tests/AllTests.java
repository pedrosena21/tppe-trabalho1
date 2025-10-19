package tppe.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({
    SorteioRodadasTest.class,
    PartidaTest.class,
    TimeTest.class
})
class AllTests {
   
}