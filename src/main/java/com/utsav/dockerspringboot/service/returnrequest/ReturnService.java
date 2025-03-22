package com.utsav.dockerspringboot.service.returnrequest;

import com.utsav.dockerspringboot.model.ReturnRequest;
import org.springframework.stereotype.Service;

public interface ReturnService {
    ReturnRequest initiateReturn(Long orderItemId, String reason);
    void processReturn(Long returnRequestId, String action, String refundMethod);
}
