# EnerNOC OpenADR 2.0 Reference Implementation #

This is part of EnerNOC's open source reference implementation for the OpenADR 2.0 spec. Credit goes to Thom Nichols for the JAXB bindings,
Packet Extension using Smack, and easy build management process via Maven. Original source code here:

https://github.com/EnerNOC/oadr2-ven

See: http://openadr.org/

This project provides the building blocks for implementing a VEN client.  In particular,
it aims to provide protocol bindings.  It *does not* actually implement any service 
functionality (at least not yet.)  But it should provide enough at least for someone to 
start implementing OpenADR 2.0 services. 

## Modules ##

**oadr2-model** Simple JAXB Bindings

**oadr2-xmpp** XMPP client implementation

**oadr2-app** XMPP client application

**oadr2-http** HTTP client implementation (TODO)

**oadr2-vtn** This is a separate project: https://github.com/EnerNOC/oadr2-vtn


## Development, Build, Testing ##

Use Maven: http://maven.apache.org

Build by running `maven compile` from this directory.  Dependencies will be downloaded automatically.  

To run XMPP unit tests with your Google Talk account, do the following:

    mvn test -Dxmpp-username="my_username@gmail.com" -Dxmpp-pass="blah"

To see debug output from the XMPP stream, add the following flag:
`-Dsmack.debugEnabled=true`


Alternatively, to run as a Java application, execute "setup" and "run" inside the terminal:

   ./setup
   
   ./run


## License ##

This code is released under the Apache 2.0 software license. Please take it and use the code in your (commercial or open source) product. Contributions back to this project are also welcome!

==================
