package com.cloudestudio.hosgetserver.model;

import lombok.Data;

/**
 * @Class PatientInfReport
 * @Author Create By Matrix·张
 * @Date 2024/12/16 下午10:13
 * 传染病报告卡实体类
 */
@Data
public class PatientInfReport {
    private String id;
    private String patient_id;
    private String serial_number;
    private String activity_type_code;
    private String activity_type_name;
    private String patient_name;
    private String id_card_type_code;
    private String id_card_type_name;
    private String id_card;
    private String gender_code;
    private String gender_name;
    private String birth_date;
    private String nationality_code;
    private String nationality_name;
    private String nation_code;
    private String nation_name;
    private String permanent_addr_code;
    private String permanent_addr_name;
    private String permanent_addr_detail;
    private String current_addr_code;
    private String current_addr_name;
    private String current_addr_detail;
    private Object workunit;
    private Object marital_status_code;
    private String marital_status_name;
    private String education_code;
    private String education_name;
    private String nultitude_type_code;
    private String nultitude_type_name;
    private String nultitude_type_other;
    private String tel;
    private String contacts;
    private String contacts_tel;
    private String onset_date;
    private String diagnose_time;
    private String disease_code;
    private String disease_name;
    private String disease_other;
    private String diagnose_state_code;
    private String diagnose_state_name;
    private String case_type_code;
    private String case_type_name;
    private String dead_date;
    private String is_dead_by_this_code;
    private String is_dead_by_this_name;
    private String symptoms_code;
    private String symptoms_name;
    private String verdict_code;//laboratory_detection_verdict_code
    private String verdict_name;//laboratory_detection_verdict_name
    private String detection_positive_date;
    private String detection_org_code;
    private String dt_diagnose;
    private String afp_areatype1_code;
    private String afp_areatype1name;
    private String afp_palsy_date;
    private String afp_doctor_date;
    private String afp_areatype2_code;
    private String afp_areatype2_name;
    private String afp_addrcode_code;
    private String afp_addrcode_name;
    private String afp_addr;
    private String afp_palsy_symptom;
    private String report_date;
    private String discovery_mode_code;
    private String discovery_mode_name;
    private String discovery_mode_other;
    private String venereal_dis_code;
    private String venereal_dis_name;
    private String bs_transmission_code;
    private String bs_transmission_name;
    private String bs_transmission_other;
    private String contact_type_code;
    private String contact_type_name;
    private String inject_count;
    private String nonweb_count;
    private String sm_count;
    private String contact_other;
    private String sinfect_code;
    private String sinfect_name;
    private String serverity_code;
    private String serverity_name;
    private String lab_result_code;
    private String lab_result_name;
    private String hbsag_code;
    private String hbsag_name;
    private String hbsag_first;
    private String hbsag_buxiang;
    private String hbsag_alt;
    private String hbcig_result_code;
    private String hbcig_result_name;
    private String hbliver_puncture_code;
    private String hbliver_puncture_name;
    private String hbsag_changename;
    private String contactflag_code;
    private String contactflag_name;
    private String fill_doctor;
    private String notes;
    private String ncv_severity_code;
    private String ncv_severity_name;
    private String foreign_type_code;
    private String foreign_type_name;
    private String place_code;
    private String place_name;
    private String report_zone_code;
    private String report_zone_name;
    private String report_org_code;
    private String report_org_name;
    private String dept_code;
    private String dept_name;
    private String operator_id;
    private String operation_time;
}
