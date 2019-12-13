package com.arnouldcoulon.subway_shortest_paths;

import java.util.List;

import com.arnouldcoulon.subway.Correspondance;
import com.arnouldcoulon.subway.Station;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}


//Ajouter dans un test ? //


/* Exemple où on récupère les correspondances d'une station donnée 
Station s = stations.get("1694");
//Si cette station a des correspondances
if(Correspondance.hasCorrespondances(s)) {
	// On récupère une liste de station qui sont les correspondances de la station donnée 
	List<Station> ls = Correspondance.getStationCorrespondances(s);
	for(Station correspStation: ls) {
		// Ajout d'un edge entre cette station et la correspondance par exemple 
		// s.addEdge(correspStation);
	}
}
*/