Steps:

Clone project

unzip the project

import to eclise as maven project

right click and run the project as SpringBoot Application

Before Run the app, please make sure ActiveMQ is in running.

http://localhost:8161/admin

create 2 queues:
  
  1) inbound.queue
  2)outbound.queue
  
Put the below json in input queues:

1) 

<MBRequest>
    <clientId>CITI</clientId>
    <Remittance>
	<paymentId>X212</paymentId>
	<amount>1200</amount>
	<currency>inr</currency>
    </Remittance> 
</MBRequest>


  2) 
  
  {
  "MBRequest": {
    "clientId": "CITI",
    "Remittance": {
      "paymentId": "X212",
      "amount": "1200",
      "currency": "inr"
    }
  }
}
