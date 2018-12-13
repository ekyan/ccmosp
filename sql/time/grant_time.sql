GRANT SELECT, INSERT, UPDATE, DELETE ON 
tmd_attendance,
tmd_attendance_correction,
tmd_allowance,
tmd_rest,
tmd_go_out,
tmd_time_record,
tmd_total_time,
tmd_total_time_correction,
tmd_total_leave,
tmd_total_other_vacation,
tmd_total_absence,
tmd_total_allowance,
tmt_total_time,
tmt_total_time_employee,
tmd_overtime_request,
tmd_holiday_request,
tmd_work_on_holiday_request,
tmd_sub_holiday_request,
tmd_work_type_change_request,
tmd_difference_request,
tmm_time_setting,
tmm_limit_standard,
tmm_work_type,
tmm_work_type_item,
tmm_work_type_pattern,
tma_work_type_pattern_item,
tmm_paid_holiday,
tmm_paid_holiday_first_year,
tmm_paid_holiday_point_date,
tmm_paid_holiday_entrance_date,
tmm_stock_holiday,
tmm_holiday,
tmd_holiday,
tmd_paid_holiday,
tmt_paid_holiday,
tmd_timely_paid_holiday,
tmd_stock_holiday,
tmt_stock_holiday,
tmm_schedule,
tmm_schedule_date,
tmm_cutoff,
tmm_application,
tmd_sub_holiday,
tmd_substitute,
tmt_attendance
 TO usermosp;

GRANT USAGE, SELECT, UPDATE ON 
tmd_attendance_id_seq,
tmd_attendance_correction_id_seq,
tmd_allowance_id_seq,
tmd_rest_id_seq,
tmd_go_out_id_seq,
tmd_time_record_id_seq,
tmd_total_time_id_seq,
tmd_total_time_correction_id_seq,
tmd_total_leave_id_seq,
tmd_total_other_vacation_id_seq,
tmd_total_absence_id_seq,
tmd_total_allowance_id_seq,
tmt_total_time_id_seq,
tmt_total_time_employee_id_seq,
tmd_overtime_request_id_seq,
tmd_holiday_request_id_seq,
tmd_work_on_holiday_request_id_seq,
tmd_sub_holiday_request_id_seq,
tmd_work_type_change_request_id_seq,
tmd_difference_request_id_seq,
tmd_substitute_id_seq,
tmd_sub_holiday_id_seq,
tmm_time_setting_id_seq,
tmm_limit_standard_id_seq,
tmm_work_type_id_seq,
tmm_work_type_item_id_seq,
tmm_work_type_pattern_id_seq,
tma_work_type_pattern_item_id_seq,
tmm_paid_holiday_id_seq,
tmm_paid_holiday_first_year_id_seq,
tmm_paid_holiday_point_date_id_seq,
tmm_paid_holiday_entrance_date_id_seq,
tmm_stock_holiday_id_seq,
tmm_holiday_id_seq,
tmd_holiday_id_seq,
tmd_paid_holiday_id_seq,
tmt_paid_holiday_id_seq,
tmd_timely_paid_holiday_id_seq,
tmd_stock_holiday_id_seq,
tmt_stock_holiday_id_seq,
tmm_schedule_id_seq,
tmm_schedule_date_id_seq,
tmm_cutoff_id_seq,
tmm_application_id_seq,
tmt_attendance_id_seq
 TO usermosp;
