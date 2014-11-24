package com.epichomeservices.powerballpoolmanager;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RSSparser {
	
	String[] lottoFeedString = new String[4];
	
	public String[] parse() {
		
		
		URL url;
		try {
			// Set the url (you will need to change this to your RSS URL
			url = new URL("http://flalottery.com/video/en/theWinningNumber.xml");

			// Setup the connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// Connect
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				// Retreive the XML from the URL
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc;
				doc = db.parse(url.openStream());
				doc.getDocumentElement().normalize();

				// This is the root node of each section you want to parse
				NodeList itemLst = doc.getElementsByTagName("item");
				
				String pb = "powerball";
				String mm = "megamillions";

				// Loop through the XML passing the data to the arrays
				for (int i = 0; i < itemLst.getLength(); i++) {

					Node item = itemLst.item(i);
					if (item.getNodeType() == Node.ELEMENT_NODE) {

						if (item.getAttributes().item(0).getNodeValue()
								.equals(pb)) {
							lottoFeedString[0] = item.getAttributes().item(4)
									.getNodeValue();
							lottoFeedString[1] = item.getAttributes().item(6)
									.getNodeValue();
														

						} else if (item.getAttributes().item(0).getNodeValue()
								.equals(mm)) {
							lottoFeedString[2] = item.getAttributes().item(3)
									.getNodeValue();
							lottoFeedString[3] = item.getAttributes().item(4)
									.getNodeValue();
							
						}

					}
				}
			
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		return lottoFeedString;

	}
	
}
