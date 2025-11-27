package tppe.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({
	CampeonatoTest.class,
    SorteioRodadasTest.class,
    PartidaTest.class,
    TimeTest.class,
    RodadaTest.class
})
class AllTests {
   
}