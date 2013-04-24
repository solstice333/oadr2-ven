package org.enernoc.open.oadr2.xmpp;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.enernoc.open.oadr2.model.OadrCreatedEvent;
import org.enernoc.open.oadr2.model.OadrDistributeEvent;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

public class OadrApp {
   static String username;
   static String password;
   static boolean isVen;
   static ConnHandler connHandler = new ConnHandler();
   static XMPPConnection ven = null, vtn = null;
   static final String OADR2_XMLNS = "http://openadr.org/oadr-2.0a/2012/07";

   public static void main(String[] args)
         throws DatatypeConfigurationException, FileNotFoundException,
         JAXBException, XMPPException, InterruptedException {

      XMPPConnection.DEBUG_ENABLED = true;

      @SuppressWarnings("resource")
      Scanner s = new Scanner(System.in);

      loginProcedure();

      // vtn operations
      if (!isVen) {
         // connect
         vtnConnect();

         // display roster, select contact
         while (true) {
            try {
               connHandler.displayVtnRoster();
            }
            catch (InterruptedException e1) {
               e1.printStackTrace();
            }
            System.out.println("------------------------");
            System.out
                  .println("Who do you want to send a payload to?"
                        + "\nType contacts full JID (including resource, i.e. contact@domain.com/resource). "
                        + "\nType 'refresh()' to refresh list and presence status:"
                        + "\nType 'quit()' to quit");

            String to = "";
            s = new Scanner(System.in);
            boolean valid = false;

            while (!valid) {
               to = s.nextLine();
               valid = true;

               if (to.equals("quit()")) {
                  System.out.println("Quitting...");
                  connHandler.disconnect();
                  System.exit(0);
               }
               else if (to.equals("refresh()")) {
                  valid = false;

                  try {
                     connHandler.displayVtnRoster();
                  }
                  catch (InterruptedException e) {
                     e.printStackTrace();
                  }

                  System.out.println("------------------------");
                  System.out
                        .println("Who do you want to send a payload to?"
                              + "\nType contacts full JID. "
                              + "\nType 'refresh' to refresh list and presence status:"
                              + "\nType 'quit()' to quit");
               }
               else if (!connHandler.checkIfExists(to, vtn.getRoster())) {
                  valid = false;
                  System.out.println("Contact does not exist. Try again: ");
               }
            }

            System.out.println("---------------------------");
            System.out.println("All payloads will be sent to: "
                  + to
                  + ", name: "
                  + vtn.getRoster().getEntry(connHandler.getBareJid())
                        .getName());
            System.out.println("---------------------------");

            // Create IQ with oadr packet extension and send to "to"
            while (true) {
               System.out
                     .println("Do you want to send an OadrDistributeEvent payload to "
                           + to
                           + "? Type 'yes' or 'no' \n Type 'quit()' to quit"
                           + "\n Type 'reselect()' to select new contact");
               String ans = s.next();

               if (ans.equals("yes")) {
                  OadrDistributeEvent ode = new OadrPayloadFactory().createEventPayload();
                  IQ iq = connHandler.createIQ(to, ode);
                  vtn.sendPacket(iq);
                  
                  System.out.println("Sending the following oadrDistributeEvent payload to " + to + ": ");
                  ConnHandler.testNamespace(ode);
               }

               else if (ans.equals("no"))
                  System.out.println("Message not sent");

               else if (ans.equals("quit()")) {
                  System.out.println("Quitting...");
                  connHandler.disconnect();
                  System.exit(0);
               }

               else if (ans.equals("reselect()"))
                  break;

               else
                  System.out
                        .println("Invalid answer. 'yes', 'no', 'reselect', or 'quit()'");
            }
         }
      }

      // ven operations
      else {
         venConnect();

         //add listener to ven
         PacketListener oadrDistributeEventListener = new PacketListener() {
            @Override
            public void processPacket(Packet packet) {
               System.out.println("OadrDistributeEvent payload received!");
               
               // grab OadrDistributeEvent payload
               OADR2PacketExtension oadrExtension = (OADR2PacketExtension) packet
                     .getExtension(OADR2_XMLNS);
               OadrDistributeEvent ode = (OadrDistributeEvent) oadrExtension
                     .getPayload();

               // parsing oadrDistributeEvent payload for relevant elements and
               // instantiating OadrCreatedEvent payload via factory method
               System.out.println("parsing oadrDistributeEvent payload...");
               OadrCreatedEvent oce = new OadrPayloadFactory()
                     .createResponsePayload(ode);

               // print xml of OadrCreatedEvent payload
               try {
                  System.out.println("createdEventPayload: ");
                  ConnHandler.testNamespace(oce);
                  OADR2PacketExtension pe = new OADR2PacketExtension(oce,
                        new JAXBManager());
                  System.out.println("xmlns: " + pe.getNamespace());
               }
               catch (Exception e) {
                  e.printStackTrace();
               }
               finally {
                  System.out.println();
               }

               // creates IQ packet for oadrCreatedEvent and sends to vtn
               System.out.println("sending oadrCreatedEvent payload...");
               try {
                  IQ oceiq = new ConnHandler().createIQ(packet.getFrom(), oce);
                  ven.sendPacket(oceiq);
               }
               catch (Exception e) {
                  e.printStackTrace();
               }

               System.out.println("oadrCreatedEvent payload sent!");
            }
         };
         ven.addPacketListener(oadrDistributeEventListener,
               new OADR2PacketFilter());

         while (true) {
            System.out
                  .println("Would you like to quit or poll the packet collector queue? Type 'quit()' or 'poll()'"
                        + "\nType 'show()' to show buddy list");
            String ans = s.next();

            if (ans.equals("quit()")) {
               System.out.println("Quitting...");
               connHandler.disconnect();
               System.exit(0);
            }
            else if (ans.equals("poll()")) {
               try {
                  System.out.println("packetCollector disabled");
               }
               catch (AssertionError ae) {
                  System.out.println("AssertionError thrown");
               }
               catch (NullPointerException npe) {
                  System.out.println("NullPointerException thrown");
               }
            }
            else if (ans.equals("show()")) {
               connHandler.displayVenRoster();
            }
            else
               System.out.println("Invalid answer");

         }
      }
   }

   private static boolean VenOrVtn(String msg) {
      @SuppressWarnings("resource")
      Scanner s = new Scanner(System.in);
      boolean isVen = true, valid = false;

      while (!valid) {
         String ans = s.next();
         valid = true;
         if (ans.equals("ven"))
            isVen = true;
         else if (ans.equals("vtn"))
            isVen = false;
         else {
            System.err.println("      Invalid choice. " + msg);
            valid = false;
         }
      }

      return isVen;
   }

   private static boolean vtnConnect() {
      boolean pass = false;

      while (!pass) {
         try {
            vtn = connHandler.connect(username, password, "talk.google.com",
                  "vtn");
            connHandler.setVtnConnection(vtn);
            return true;
         }
         catch (XMPPException e) {
            System.out.println("Login error. Invalid username or password."
                  + "\nTry again:");
            loginProcedure();
            pass = vtnConnect();
         }
      }

      return pass;
   }

   private static boolean venConnect() {
      boolean pass = false;

      while (!pass) {
         try {
            ven = connHandler.connect(username, password, "talk.google.com",
                  "ven");
            connHandler.setVenConnection(ven);
            return true;
         }
         catch (XMPPException e) {
            System.out.println("Login error. Invalid username or password."
                  + "\nTry again:");
            loginProcedure();
            pass = venConnect();
         }
      }

      return pass;
   }

   private static void loginProcedure() {
      @SuppressWarnings("resource")
      Scanner s = new Scanner(System.in);

      System.out.println("login: ");
      System.out.print("   username: ");
      username = s.next();
      System.out.print("   password: ");
      password = s.next();

      // check if vtn or ven
      System.out.print("   ven or vtn? (Type 'ven' or 'vtn' to specify): ");
      isVen = VenOrVtn("Type 'ven' or 'vtn' to specify");
   }

   private static void printPacket (Packet packet) {
      final String OADR2_xmlns = "http://openadr.org/oadr-2.0a/2012/07";

      OADR2PacketExtension ope = (OADR2PacketExtension) packet
            .getExtension(OADR2_xmlns);

      if(packet == null) {
         System.err.println("packet is null! Exiting...");
         System.exit(1);
      }
      System.out.println("packet id: " + packet.getPacketID());
      System.out.println("packet from: " + packet.getFrom());
      System.out.println("packet class: " + packet.getClass());
      System.out.println("packet namespace: " + packet.getXmlns());
      System.out.println("packet extension class: " + ope.getClass());
      System.out.println("packet extension namespace: " + ope.getNamespace());
      System.out.println("packet extension element name: "
            + ope.getElementName());

      System.out.println(((OADR2PacketExtension) packet
            .getExtension(OADR2_xmlns)).toXML());
   }

}
