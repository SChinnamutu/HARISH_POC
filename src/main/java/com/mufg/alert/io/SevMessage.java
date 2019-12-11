package com.mufg.alert.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SevMessage {
    
	private String sevHost;
    private String sevDate;
    private String sevTime;
    private String sevTopic;
    private String sevErrMsg;
    
    
}

