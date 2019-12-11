Pull the ActiveMQ image and run it first with below command.

docker run --name activemq -p 8161:8161 rmohr/activemq

Access with below url.

http://localhost:8161/


## How to run application

- Clone the application
- Build the application with Maven
  ```
  mvn install
  ```
- Then just start the containers
  ```
  docker-compose up
  ```


Drop the data in IN_QUEUE and get the output QUEUE.

Input:

<LOGGER>
    <FIELD type="general" name="host_node">MQSIPROD</FIELD>
    <FIELD type="general" name="appl_type">EPP_FLOW</FIELD>
    <FIELD type="general" name="svc_name">EPP_ERROR</FIELD>
    <FIELD type="general" name="svc_type">MF</FIELD>
    <FIELD type="general" name="pgm_id">45200</FIELD>
    <FIELD type="general" name="appl_id">EPP_GPP</FIELD>
    <FIELD type="general" name="trans_id">999</FIELD>
    <FIELD type="general" name="user_id">SYS</FIELD>
    <FIELD type="general" name="user_ref">N/A</FIELD>
    <FIELD type="general" name="log_severity">1</FIELD>
    <FIELD type="general" name="log_narrative">
        <![CDATA[S_InPTMapFailed - Inbound Transmission Mapping Failed - EPP_GPP;SEV1;SYS - Inbound Physical Transmission - TRANSMISSION - TRAM_BESS - GPP TRAM FX/MM to BESS (Mst Req) - 1518201406 - 3143 - TRAM - 2016-11-17T11:25:00.895043 ]]>
    </FIELD>
    <FIELD type="general" name="timestp_source">2016-11-17 11:25:01.029484</FIELD>
    <FIELD type="err" seq="1" name="cd_file">SYS</FIELD>
    <FIELD type="err" seq="1" name="id_err_typ">999</FIELD>
    <FIELD type="err" seq="1" name="cd_svrty_lvl">1</FIELD>
    <FIELD type="err" seq="1" name="tx_error">
        <![CDATA[S_InPTMapFailed - Inbound Transmission Mapping Failed - EPP_GPP;SEV1;SYS - Inbound Physical Transmission - TRANSMISSION - TRAM_BESS - GPP TRAM FX/MM to BESS (Mst Req) - 1518201406 - 3143 - TRAM - 2016-11-17T11:25:00.895043 ]]>
    </FIELD>
</LOGGER>

Output:

<LOGGER>
    <FIELD type="general" name="host_node">MQSIPROD</FIELD>
    <FIELD type="general" name="appl_type">EPP_FLOW</FIELD>
    <FIELD type="general" name="svc_name">EPP_ERROR</FIELD>
    <FIELD type="general" name="svc_type">MF</FIELD>  
</LOGGER>
