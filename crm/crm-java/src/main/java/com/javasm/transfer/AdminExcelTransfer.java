package com.javasm.transfer;

import com.javasm.domin.entity.Admin;
import com.javasm.domin.excel.AdminExcel;
import com.javasm.transfer.base.BaseTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminExcelTransfer extends BaseTransfer<Admin, AdminExcel> {

}
