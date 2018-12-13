CREATE TABLE com_college_human
(
  com_college_human_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  activate_date date NOT NULL,
  prescribed_working_days_per_week integer NOT NULL DEFAULT 0,
  prescribed_working_days_per_year integer NOT NULL DEFAULT 0,
  prescribed_working_hours_per_week double precision NOT NULL DEFAULT 0,
  holiday_college_type integer NOT NULL DEFAULT 0,
  holiday_payment_rate_per_hour double precision NOT NULL DEFAULT 0,
  holiday_hours_per_day double precision NOT NULL DEFAULT 0,
  holiday_payment_rate_per_day double precision NOT NULL DEFAULT 0,
  holiday_fund_code text NOT NULL DEFAULT ''::text,
  holiday_approval_route character varying(120) NOT NULL DEFAULT '',
  united_overtime_fund_code text NOT NULL DEFAULT ''::text,
  united_monthly_approval_route character varying(120) NOT NULL DEFAULT '',
  attendance_rate_flag integer NOT NULL DEFAULT 0,
  transportation_expenses_a_name character varying(30) NOT NULL DEFAULT '',
  transportation_expenses_b_name character varying(30) NOT NULL DEFAULT '',
  transportation_expenses_c_name character varying(30) NOT NULL DEFAULT '',
  transportation_expenses_a_abbr character varying(6) NOT NULL DEFAULT '',
  transportation_expenses_b_abbr character varying(6) NOT NULL DEFAULT '',
  transportation_expenses_c_abbr character varying(6) NOT NULL DEFAULT '',
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL,
  CONSTRAINT com_college_human_pkey PRIMARY KEY (com_college_human_id)
);
COMMENT ON TABLE com_college_human IS '複数身分人事マスタ';
COMMENT ON COLUMN com_college_human.com_college_human_id IS 'レコード識別ID';
COMMENT ON COLUMN com_college_human.personal_id IS '個人ID';
COMMENT ON COLUMN com_college_human.activate_date IS '有効日';
COMMENT ON COLUMN com_college_human.prescribed_working_days_per_week IS '合算所定労働日数/週';
COMMENT ON COLUMN com_college_human.prescribed_working_days_per_year IS '合算所定労働日数/年';
COMMENT ON COLUMN com_college_human.prescribed_working_hours_per_week IS '合算所定労働時間/週';
COMMENT ON COLUMN com_college_human.holiday_college_type IS '休暇取得方式区分';
COMMENT ON COLUMN com_college_human.holiday_payment_rate_per_hour IS '休暇利用時の単価';
COMMENT ON COLUMN com_college_human.holiday_hours_per_day IS '休暇時間数/日';
COMMENT ON COLUMN com_college_human.holiday_payment_rate_per_day IS '休暇日給';
COMMENT ON COLUMN com_college_human.holiday_fund_code IS '休暇資金元コード';
COMMENT ON COLUMN com_college_human.holiday_approval_route IS '休暇承認ルート';
COMMENT ON COLUMN com_college_human.united_overtime_fund_code IS '合算時の残業資金元コード';
COMMENT ON COLUMN com_college_human.united_monthly_approval_route IS '合算の月締め承認ルート';
COMMENT ON COLUMN com_college_human.attendance_rate_flag IS '8割出勤チェックフラグ';
COMMENT ON COLUMN com_college_human.transportation_expenses_a_name IS '交通費A名称';
COMMENT ON COLUMN com_college_human.transportation_expenses_b_name IS '交通費B名称';
COMMENT ON COLUMN com_college_human.transportation_expenses_c_name IS '交通費C名称';
COMMENT ON COLUMN com_college_human.transportation_expenses_a_abbr IS '交通費A略称';
COMMENT ON COLUMN com_college_human.transportation_expenses_b_abbr IS '交通費B略称';
COMMENT ON COLUMN com_college_human.transportation_expenses_c_abbr IS '交通費C略称';
COMMENT ON COLUMN com_college_human.delete_flag IS '削除フラグ';
COMMENT ON COLUMN com_college_human.insert_date IS '登録日';
COMMENT ON COLUMN com_college_human.insert_user IS '登録者';
COMMENT ON COLUMN com_college_human.update_date IS '更新日';
COMMENT ON COLUMN com_college_human.update_user IS '更新者';

CREATE TABLE com_fund
(
  com_fund_id bigint NOT NULL DEFAULT 0,
  fund_code text NOT NULL DEFAULT ''::text,
  activate_date date NOT NULL,
  start_year integer NOT NULL DEFAULT 0,
  research_task_number character varying(10) NOT NULL DEFAULT 0,
  research_task_type integer NOT NULL DEFAULT 0,
  research_task_name  character varying(100) NOT NULL DEFAULT '',
  research_task_formal_name  text NOT NULL DEFAULT ''::text,
  researcher_code character varying(10) NOT NULL DEFAULT ''::character varying,
  researcher_name character varying(50) NOT NULL DEFAULT ''::character varying,
  fund_approval_route character varying(120) NOT NULL DEFAULT '',
  over_time_flag integer NOT NULL DEFAULT 0,
  over_time_fund_code text NOT NULL DEFAULT ''::text,
  holiday_flag integer NOT NULL DEFAULT 0,
  holiday_fund_code text NOT NULL DEFAULT ''::text,
  transportation_expenses_flag integer NOT NULL DEFAULT 0,
  health_insurance_calc_flag integer NOT NULL DEFAULT 0,
  welfare_pension_calc_flag integer NOT NULL DEFAULT 0,
  employment_insurance_calc_flag integer NOT NULL DEFAULT 0,
  care_insurance_calc_flag integer NOT NULL DEFAULT 0,
  worker_accident_insurance_calc_flag integer NOT NULL DEFAULT 0,
  child_allowance_calc_flag integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT com_fund_pkey PRIMARY KEY (com_fund_id)
);
COMMENT ON TABLE com_fund IS '資金元マスタ';
COMMENT ON COLUMN com_fund.com_fund_id IS 'レコード識別ID';
COMMENT ON COLUMN com_fund.fund_code IS '資金コード';
COMMENT ON COLUMN com_fund.activate_date IS '有効日';
COMMENT ON COLUMN com_fund.start_year IS '開始年度';
COMMENT ON COLUMN com_fund.research_task_number IS '研究課題番号';
COMMENT ON COLUMN com_fund.research_task_type IS '研究課題区分';
COMMENT ON COLUMN com_fund.research_task_name IS '研究課題名称';
COMMENT ON COLUMN com_fund.research_task_formal_name IS '研究課題正式名称';
COMMENT ON COLUMN com_fund.researcher_code IS '研究代表者コード';
COMMENT ON COLUMN com_fund.researcher_name IS '研究代表者氏名';
COMMENT ON COLUMN com_fund.fund_approval_route IS '資金元承認ルート';
COMMENT ON COLUMN com_fund.over_time_flag IS '残業可否フラグ';
COMMENT ON COLUMN com_fund.over_time_fund_code IS '残業資金元コード';
COMMENT ON COLUMN com_fund.holiday_flag IS '休暇可否フラグ';
COMMENT ON COLUMN com_fund.holiday_fund_code IS '休暇資金元コード';
COMMENT ON COLUMN com_fund.transportation_expenses_flag IS '交通費可否フラグ';
COMMENT ON COLUMN com_fund.health_insurance_calc_flag IS '健康保険計算フラグ';
COMMENT ON COLUMN com_fund.welfare_pension_calc_flag IS '厚生年金計算フラグ';
COMMENT ON COLUMN com_fund.employment_insurance_calc_flag IS '雇用保険計算フラグ';
COMMENT ON COLUMN com_fund.care_insurance_calc_flag IS '介護保険計算フラグ';
COMMENT ON COLUMN com_fund.worker_accident_insurance_calc_flag IS '労災保険計算フラグ';
COMMENT ON COLUMN com_fund.child_allowance_calc_flag IS '児童手当計算フラグ';
COMMENT ON COLUMN com_fund.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN com_fund.delete_flag IS '削除フラグ';
COMMENT ON COLUMN com_fund.insert_date IS '登録日';
COMMENT ON COLUMN com_fund.insert_user IS '登録者';
COMMENT ON COLUMN com_fund.update_date IS '更新日';
COMMENT ON COLUMN com_fund.update_user IS '更新者';


CREATE TABLE com_standing
(
  com_standing_id bigint NOT NULL DEFAULT 0,
  standing_code character varying(21) NOT NULL DEFAULT '',
  activate_date date NOT NULL,
  standing_name text NOT NULL DEFAULT '',
  standing_fund_code text NOT NULL DEFAULT ''::text,
  personal_id character varying(10) NOT NULL DEFAULT '',
  standing_payment_rate double precision NOT NULL DEFAULT 0,
  overtime_fund_code text NOT NULL DEFAULT ''::text,
  holiday_fund_code text NOT NULL DEFAULT ''::text,
  daily_approval_route character varying(120) NOT NULL DEFAULT '',
  monthly_approval_route character varying(120) NOT NULL DEFAULT '',
  standing_quit_date date,
  fixed_time_flag integer NOT NULL DEFAULT 0,
  provisional_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL,
  CONSTRAINT com_standing_pkey PRIMARY KEY (com_standing_id)
);
COMMENT ON TABLE com_standing IS '身分マスタ';
COMMENT ON COLUMN com_standing.com_standing_id IS 'レコード識別ID';
COMMENT ON COLUMN com_standing.standing_code IS '身分コード';
COMMENT ON COLUMN com_standing.activate_date IS '有効日';
COMMENT ON COLUMN com_standing.standing_name IS '身分名称';
COMMENT ON COLUMN com_standing.standing_fund_code IS '資金元コード';
COMMENT ON COLUMN com_standing.personal_id IS '個人ID';
COMMENT ON COLUMN com_standing.standing_payment_rate IS '身分単価';
COMMENT ON COLUMN com_standing.overtime_fund_code IS '残業資金元コード';
COMMENT ON COLUMN com_standing.holiday_fund_code IS '休暇資金元コード';
COMMENT ON COLUMN com_standing.daily_approval_route IS '日々承認ルート';
COMMENT ON COLUMN com_standing.monthly_approval_route IS '月締め承認ルート';
COMMENT ON COLUMN com_standing.standing_quit_date IS '退職日';
COMMENT ON COLUMN com_standing.fixed_time_flag IS '未定チェック';
COMMENT ON COLUMN com_standing.provisional_flag IS '仮登録フラグ';
COMMENT ON COLUMN com_standing.delete_flag IS '削除フラグ';
COMMENT ON COLUMN com_standing.insert_date IS '登録日';
COMMENT ON COLUMN com_standing.insert_user IS '登録者';
COMMENT ON COLUMN com_standing.update_date IS '更新日';
COMMENT ON COLUMN com_standing.update_user IS '更新者';

CREATE TABLE com_standard_pay(
  com_standard_pay_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calc_year integer NOT NULL DEFAULT 0,
  calc_month integer NOT NULL DEFAULT 0,
  standard_pay integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT com_standard_pay_pkey PRIMARY KEY (com_standard_pay_id)
)
;
COMMENT ON TABLE com_standard_pay IS '標準報酬月額マスタ';
COMMENT ON COLUMN com_standard_pay.com_standard_pay_id IS 'レコード識別ID';
COMMENT ON COLUMN com_standard_pay.personal_id IS '個人ID';
COMMENT ON COLUMN com_standard_pay.calc_year IS '計算年';
COMMENT ON COLUMN com_standard_pay.calc_month IS '計算月';
COMMENT ON COLUMN com_standard_pay.standard_pay IS '標準報酬月額';
COMMENT ON COLUMN com_standard_pay.delete_flag IS '削除フラグ';
COMMENT ON COLUMN com_standard_pay.insert_date IS '登録日';
COMMENT ON COLUMN com_standard_pay.insert_user IS '登録者';
COMMENT ON COLUMN com_standard_pay.update_date IS '更新日';
COMMENT ON COLUMN com_standard_pay.update_user IS '更新者';

CREATE TABLE cod_adopt_request
(
  cod_adopt_request_id bigint NOT NULL DEFAULT 0,
  standing_code text NOT NULL DEFAULT '',
  activate_date date NOT NULL,
  personal_id character varying(10) NOT NULL DEFAULT '',
  adopt_code bigint NOT NULL DEFAULT 0,
  adopt_fund_type  integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  create_personal_id character varying(10) NOT NULL DEFAULT '',
  start_date date NOT NULL,
  end_date date,
  applicant_state integer NOT NULL DEFAULT 0,
  personal_state integer NOT NULL DEFAULT 0,
  research_support_state integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL,
  CONSTRAINT cod_adopt_request_pkey PRIMARY KEY (cod_adopt_request_id)
);
COMMENT ON TABLE cod_adopt_request IS '採用情報マスタ';
COMMENT ON COLUMN cod_adopt_request.cod_adopt_request_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_adopt_request.standing_code IS '身分コード';
COMMENT ON COLUMN cod_adopt_request.activate_date IS '有効日';
COMMENT ON COLUMN cod_adopt_request.personal_id IS '個人ID';
COMMENT ON COLUMN cod_adopt_request.adopt_code IS '採用コード';
COMMENT ON COLUMN cod_adopt_request.adopt_fund_type IS '採用申請資金区分';
COMMENT ON COLUMN cod_adopt_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_adopt_request.create_personal_id IS '作成者個人ID';
COMMENT ON COLUMN cod_adopt_request.start_date IS '期間開始日';
COMMENT ON COLUMN cod_adopt_request.end_date IS '期間終了日';
COMMENT ON COLUMN cod_adopt_request.applicant_state IS '申請者フラグ';
COMMENT ON COLUMN cod_adopt_request.personal_state IS '人事部フラグ';
COMMENT ON COLUMN cod_adopt_request.research_support_state IS '研究支援フラグ';
COMMENT ON COLUMN cod_adopt_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_adopt_request.insert_date IS '登録日';
COMMENT ON COLUMN cod_adopt_request.insert_user IS '登録者';
COMMENT ON COLUMN cod_adopt_request.update_date IS '更新日';
COMMENT ON COLUMN cod_adopt_request.update_user IS '更新者';


CREATE TABLE coa_adopt_item
(
  coa_adopt_item_id bigint NOT NULL DEFAULT 0,
  adopt_code bigint NOT NULL DEFAULT 0,
  adopt_create_date date NOT NULL,
  work_place_code  character varying(10) NOT NULL DEFAULT '',
  appli_section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  appli_superior_name character varying(10) NOT NULL DEFAULT ''::character varying,
  appli_section_extension character varying(10) NOT NULL DEFAULT ''::character varying,
  charge_section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  clerk_section_name character varying(10) NOT NULL DEFAULT ''::character varying,
  clerk_section_extension character varying(10) NOT NULL DEFAULT ''::character varying,
  clerk_name character varying(10) NOT NULL DEFAULT ''::character varying,
  clerk_extension character varying(10) NOT NULL DEFAULT ''::character varying,
  appointment_type integer NOT NULL DEFAULT 0,
  human_type integer NOT NULL DEFAULT 0,
  student_id_number character varying(10) NOT NULL DEFAULT ''::character varying,
  full_part_work integer NOT NULL DEFAULT 0,
  employee_code character varying(10) NOT NULL DEFAULT ''::character varying,
  employee_state character varying(10) NOT NULL DEFAULT '',
  last_name character varying(50) NOT NULL DEFAULT ''::character varying,
  first_name character varying(50) NOT NULL DEFAULT ''::character varying,
  last_kana character varying(50) NOT NULL DEFAULT ''::character varying,
  first_kana character varying(50) NOT NULL DEFAULT ''::character varying,
  birth_date date,
  gender integer NOT NULL DEFAULT 0,
  mail_address_check integer NOT NULL DEFAULT 0,
  mail_address character varying(60) NOT NULL DEFAULT '',
  nationality character varying(10) NOT NULL DEFAULT ''::character varying,
  move_reason character varying(10) NOT NULL DEFAULT ''::character varying,
  postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying,
  postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying,
  prefecture character varying(2) NOT NULL DEFAULT ''::character varying,
  address character varying(64) NOT NULL DEFAULT ''::character varying,
  address_number character varying(64) NOT NULL DEFAULT ''::character varying,
  building character varying(64) NOT NULL DEFAULT ''::character varying,
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying,
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying,
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying,
  householder_name character varying(30) NOT NULL DEFAULT ''::character varying,
  relation character varying(30) NOT NULL DEFAULT ''::character varying,
  address_differ integer NOT NULL DEFAULT 0,
  extension character varying(30) NOT NULL DEFAULT ''::character varying,
  legal_postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying,
  legal_postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying,
  legal_prefecture character varying(2) NOT NULL DEFAULT ''::character varying,
  legal_address character varying(64) NOT NULL DEFAULT ''::character varying,
  legal_address_number character varying(64) NOT NULL DEFAULT ''::character varying,
  legal_building character varying(64) NOT NULL DEFAULT ''::character varying,
  trans_change integer NOT NULL DEFAULT 0,
  address_secret integer NOT NULL DEFAULT 0,
  phone_secret integer NOT NULL DEFAULT 0,
  employment_continue_program integer NOT NULL DEFAULT 0,
  hourly_pay_code integer NOT NULL DEFAULT 0,
  hourly_pay character varying(10) NOT NULL DEFAULT ''::character varying,
  work_time_other text NOT NULL DEFAULT ''::text,
  transportation_expenses integer NOT NULL DEFAULT 0,
  social_insurance integer NOT NULL DEFAULT 0,
  employment_insurance integer NOT NULL DEFAULT 0,
  research_funds_type integer NOT NULL DEFAULT 0,
  fund_cord text NOT NULL DEFAULT ''::text,
  fund_over_time_cord text NOT NULL DEFAULT ''::text,
  fund_holiday_cord text NOT NULL DEFAULT ''::text,
  adopt_reason text NOT NULL DEFAULT ''::text,
  other_remark text NOT NULL DEFAULT ''::text,
  special_report text NOT NULL DEFAULT ''::text,
  manager_section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  curator_name character varying(10) NOT NULL DEFAULT ''::character varying,
  remark text NOT NULL DEFAULT ''::text,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL,
  CONSTRAINT coa_adopt_item_pkey PRIMARY KEY (coa_adopt_item_id)
);
COMMENT ON TABLE coa_adopt_item IS '採用情報マスタ項目';
COMMENT ON COLUMN coa_adopt_item.coa_adopt_item_id IS 'レコード識別ID';
COMMENT ON COLUMN coa_adopt_item.adopt_code IS '採用コード';
COMMENT ON COLUMN coa_adopt_item.adopt_create_date IS '採用申請作成日';
COMMENT ON COLUMN coa_adopt_item.work_place_code IS '勤務地コード';
COMMENT ON COLUMN coa_adopt_item.appli_section_code IS '申請者所属';
COMMENT ON COLUMN coa_adopt_item.appli_superior_name IS '申請者長氏名/氏名';
COMMENT ON COLUMN coa_adopt_item.appli_section_extension IS '申請者所属長内線/内線';
COMMENT ON COLUMN coa_adopt_item.charge_section_code IS '事務担当所属';
COMMENT ON COLUMN coa_adopt_item.clerk_section_name IS '事務担当所属長氏名';
COMMENT ON COLUMN coa_adopt_item.clerk_section_extension IS '事務担当所属内線';
COMMENT ON COLUMN coa_adopt_item.clerk_name IS '事務担当者氏名';
COMMENT ON COLUMN coa_adopt_item.clerk_extension IS '事務担当内線';
COMMENT ON COLUMN coa_adopt_item.appointment_type IS '採用区分';
COMMENT ON COLUMN coa_adopt_item.human_type IS '種別';
COMMENT ON COLUMN coa_adopt_item.student_id_number IS '学籍番号';
COMMENT ON COLUMN coa_adopt_item.full_part_work IS '常勤/非常勤';
COMMENT ON COLUMN coa_adopt_item.employee_code IS '社員コード';
COMMENT ON COLUMN coa_adopt_item.employee_state IS '社員コード状態';
COMMENT ON COLUMN coa_adopt_item.last_name IS '姓';
COMMENT ON COLUMN coa_adopt_item.first_name IS '名';
COMMENT ON COLUMN coa_adopt_item.last_kana IS 'カナ姓';
COMMENT ON COLUMN coa_adopt_item.first_kana IS 'カナ名';
COMMENT ON COLUMN coa_adopt_item.birth_date IS '誕生日';
COMMENT ON COLUMN coa_adopt_item.gender IS '性別';
COMMENT ON COLUMN coa_adopt_item.mail_address_check IS 'メールアドレス確認フラグ';
COMMENT ON COLUMN coa_adopt_item.mail_address IS 'メールアドレス';
COMMENT ON COLUMN coa_adopt_item.nationality IS '国籍';
COMMENT ON COLUMN coa_adopt_item.move_reason IS '理由';
COMMENT ON COLUMN coa_adopt_item.postal_code_1 IS '郵便番号1';
COMMENT ON COLUMN coa_adopt_item.postal_code_2 IS '郵便番号2';
COMMENT ON COLUMN coa_adopt_item.prefecture IS '都道府県';
COMMENT ON COLUMN coa_adopt_item.address IS '市区町村';
COMMENT ON COLUMN coa_adopt_item.address_number IS '番地';
COMMENT ON COLUMN coa_adopt_item.building IS '建物情報';
COMMENT ON COLUMN coa_adopt_item.phone_number_1 IS '電話番号1';
COMMENT ON COLUMN coa_adopt_item.phone_number_2 IS '電話番号2';
COMMENT ON COLUMN coa_adopt_item.phone_number_3 IS '電話番号3';
COMMENT ON COLUMN coa_adopt_item.householder_name IS '世帯主氏名';
COMMENT ON COLUMN coa_adopt_item.relation IS '続柄';
COMMENT ON COLUMN coa_adopt_item.address_differ IS '住民票上住所';
COMMENT ON COLUMN coa_adopt_item.extension IS '内線';
COMMENT ON COLUMN coa_adopt_item.legal_postal_code_1 IS '住民票上郵便番号1';
COMMENT ON COLUMN coa_adopt_item.legal_postal_code_2 IS '住民票上郵便番号2';
COMMENT ON COLUMN coa_adopt_item.legal_prefecture IS '住民票上都道府県';
COMMENT ON COLUMN coa_adopt_item.legal_address IS '住民票上市区町村';
COMMENT ON COLUMN coa_adopt_item.legal_address_number IS '住民票上番地';
COMMENT ON COLUMN coa_adopt_item.legal_building IS '住民票上建物情報';
COMMENT ON COLUMN coa_adopt_item.trans_change IS '転居に伴う通勤交通費の変更有';
COMMENT ON COLUMN coa_adopt_item.address_secret IS '住所の開示不可';
COMMENT ON COLUMN coa_adopt_item.phone_secret IS '電話番号の開示不可';
COMMENT ON COLUMN coa_adopt_item.employment_continue_program IS '31日以上の雇用継続予定';
COMMENT ON COLUMN coa_adopt_item.hourly_pay_code IS '時給コード';
COMMENT ON COLUMN coa_adopt_item.hourly_pay IS '時給';
COMMENT ON COLUMN coa_adopt_item.work_time_other IS 'その他';
COMMENT ON COLUMN coa_adopt_item.transportation_expenses IS '通勤交通費';
COMMENT ON COLUMN coa_adopt_item.social_insurance IS '社会保険・厚生年金';
COMMENT ON COLUMN coa_adopt_item.employment_insurance IS '雇用保険';
COMMENT ON COLUMN coa_adopt_item.research_funds_type IS '研究費区分';
COMMENT ON COLUMN coa_adopt_item.fund_cord IS '資金元コード';
COMMENT ON COLUMN coa_adopt_item.fund_over_time_cord IS '残業資金元コード';
COMMENT ON COLUMN coa_adopt_item.fund_holiday_cord IS '休暇資金元コード';
COMMENT ON COLUMN coa_adopt_item.adopt_reason IS '採用申請理由/業務内容';
COMMENT ON COLUMN coa_adopt_item.other_remark IS 'その他連絡事項/単価設定理由';
COMMENT ON COLUMN coa_adopt_item.special_report IS '特記事項';
COMMENT ON COLUMN coa_adopt_item.manager_section_code IS '管理者/責任者所属';
COMMENT ON COLUMN coa_adopt_item.curator_name IS '責任者氏名';
COMMENT ON COLUMN coa_adopt_item.remark IS '人事備考';
COMMENT ON COLUMN coa_adopt_item.delete_flag IS '削除フラグ';
COMMENT ON COLUMN coa_adopt_item.insert_user IS '登録者';
COMMENT ON COLUMN coa_adopt_item.update_date IS '更新日';
COMMENT ON COLUMN coa_adopt_item.update_user IS '更新者';


CREATE TABLE com_college_allocation
(
  com_college_allocation_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  activate_date date NOT NULL,
  item_id integer NOT NULL DEFAULT 0,
  burden_ratio integer NOT NULL DEFAULT 0,
  standing_code text NOT NULL DEFAULT ''::text,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT com_college_allocation_pkey PRIMARY KEY (com_college_allocation_id)
);
COMMENT ON TABLE com_college_allocation IS '配分申請マスタ';
COMMENT ON COLUMN com_college_allocation.com_college_allocation_id IS 'レコード識別ID';
COMMENT ON COLUMN com_college_allocation.personal_id IS '個人ID';
COMMENT ON COLUMN com_college_allocation.activate_date IS '有効日';
COMMENT ON COLUMN com_college_allocation.item_id IS '項目番号';
COMMENT ON COLUMN com_college_allocation.burden_ratio IS '負担比率';
COMMENT ON COLUMN com_college_allocation.standing_code IS '身分コード';
COMMENT ON COLUMN com_college_allocation.delete_flag IS '削除フラグ';
COMMENT ON COLUMN com_college_allocation.insert_date IS '登録日';
COMMENT ON COLUMN com_college_allocation.insert_user IS '登録者';
COMMENT ON COLUMN com_college_allocation.update_date IS '更新日';
COMMENT ON COLUMN com_college_allocation.update_user IS '更新者';


CREATE TABLE coa_standing_time
(
  coa_standing_time_id bigint NOT NULL DEFAULT 0,
  standing_code character varying(21) NOT NULL DEFAULT '',
  activate_date date NOT NULL,
  day_of_week integer NOT NULL DEFAULT 0,
  work_start_time timestamp without time zone,
  work_end_time timestamp without time zone,
  rest_a_start_time timestamp without time zone,
  rest_a_end_time timestamp without time zone,
  rest_b_start_time timestamp without time zone,
  rest_b_end_time timestamp without time zone,
  provisional_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL,
  CONSTRAINT coa_standing_time_pkey PRIMARY KEY (coa_standing_time_id)
);
COMMENT ON TABLE coa_standing_time IS '身分時刻情報';
COMMENT ON COLUMN coa_standing_time.coa_standing_time_id IS 'レコード識別ID';
COMMENT ON COLUMN coa_standing_time.standing_code IS '身分コード';
COMMENT ON COLUMN coa_standing_time.activate_date IS '有効日';
COMMENT ON COLUMN coa_standing_time.day_of_week IS '曜日';
COMMENT ON COLUMN coa_standing_time.work_start_time IS '所定始業時刻';
COMMENT ON COLUMN coa_standing_time.work_end_time IS '所定終業時刻';
COMMENT ON COLUMN coa_standing_time.rest_a_start_time IS '休憩A入時刻';
COMMENT ON COLUMN coa_standing_time.rest_a_end_time IS '休憩A出時刻';
COMMENT ON COLUMN coa_standing_time.rest_b_start_time IS '休憩B入時刻';
COMMENT ON COLUMN coa_standing_time.rest_b_end_time IS '休憩B出時刻';
COMMENT ON COLUMN coa_standing_time.provisional_flag IS '仮登録フラグ';
COMMENT ON COLUMN coa_standing_time.delete_flag IS '削除フラグ';
COMMENT ON COLUMN coa_standing_time.insert_date IS '登録日';
COMMENT ON COLUMN coa_standing_time.insert_user IS '登録者';
COMMENT ON COLUMN coa_standing_time.update_date IS '更新日';
COMMENT ON COLUMN coa_standing_time.update_user IS '更新者';

CREATE TABLE cod_college_attendance
(
  cod_college_attendance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  work_date date NOT NULL,
  work_type_code character varying(20) NOT NULL DEFAULT '',
  paid_holiday_grant_date date,
  applicant_comment text NOT NULL DEFAULT '',
  transportation_expenses_route character varying(1) NOT NULL DEFAULT '',
  transportation_expenses_amount integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_college_attendance_pkey PRIMARY KEY (cod_college_attendance_id)
)
;
COMMENT ON TABLE cod_college_attendance IS '複数身分勤怠データ';
COMMENT ON COLUMN cod_college_attendance.cod_college_attendance_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_college_attendance.personal_id IS '個人ID';
COMMENT ON COLUMN cod_college_attendance.work_date IS '勤務日';
COMMENT ON COLUMN cod_college_attendance.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN cod_college_attendance.paid_holiday_grant_date IS '有給休暇付与日';
COMMENT ON COLUMN cod_college_attendance.applicant_comment IS '申請者コメント';
COMMENT ON COLUMN cod_college_attendance.transportation_expenses_route IS '交通費ルート';
COMMENT ON COLUMN cod_college_attendance.transportation_expenses_amount IS '交通費金額';
COMMENT ON COLUMN cod_college_attendance.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_college_attendance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_college_attendance.insert_date IS '登録日';
COMMENT ON COLUMN cod_college_attendance.insert_user IS '登録者';
COMMENT ON COLUMN cod_college_attendance.update_date IS '更新日';
COMMENT ON COLUMN cod_college_attendance.update_user IS '更新者';

CREATE TABLE cod_payment
(
  cod_payment_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calc_year integer NOT NULL DEFAULT 0,
  calc_month integer NOT NULL DEFAULT 0,
  standing_code character varying(21) NOT NULL DEFAULT '',
  fixed_flag integer NOT NULL DEFAULT 0,
  basis_pay integer NOT NULL DEFAULT 0,
  holiday_pay integer NOT NULL DEFAULT 0,
  overtime_pay integer NOT NULL DEFAULT 0,
  united_pay integer NOT NULL DEFAULT 0,
  work_on_legal_pay integer NOT NULL DEFAULT 0,
  night_in_pay integer NOT NULL DEFAULT 0,
  night_out_pay integer NOT NULL DEFAULT 0,
  night_united_pay integer NOT NULL DEFAULT 0,
  night_legal_pay integer NOT NULL DEFAULT 0,
  exceed_pay_total integer NOT NULL DEFAULT 0,
  trans_a_expense integer NOT NULL DEFAULT 0,
  trans_b_expense integer NOT NULL DEFAULT 0,
  trans_c_expense integer NOT NULL DEFAULT 0,
  trans_z_expense integer NOT NULL DEFAULT 0,
  trans_total integer NOT NULL DEFAULT 0,
  pay_total integer NOT NULL DEFAULT 0,
  standard_pay integer NOT NULL DEFAULT 0,
  health_ins_com integer NOT NULL DEFAULT 0,
  nurse_ins_com integer NOT NULL DEFAULT 0,
  welfare_ins_com integer NOT NULL DEFAULT 0,
  child_care_fund integer NOT NULL DEFAULT 0,
  employ_ins integer NOT NULL DEFAULT 0,
  workmens_ins integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT cod_payment_pkey PRIMARY KEY (cod_payment_id)
)
;
COMMENT ON TABLE cod_payment IS '複数身分給与計算データ';
COMMENT ON COLUMN cod_payment.cod_payment_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_payment.personal_id IS '個人ID';
COMMENT ON COLUMN cod_payment.calc_year IS '計算年';
COMMENT ON COLUMN cod_payment.calc_month IS '計算月';
COMMENT ON COLUMN cod_payment.standing_code IS '身分コード';
COMMENT ON COLUMN cod_payment.fixed_flag IS '計算結果取込区分';
COMMENT ON COLUMN cod_payment.basis_pay IS '本給';
COMMENT ON COLUMN cod_payment.holiday_pay IS '休暇支給額';
COMMENT ON COLUMN cod_payment.overtime_pay IS '残業手当';
COMMENT ON COLUMN cod_payment.united_pay IS '合算残業手当';
COMMENT ON COLUMN cod_payment.work_on_legal_pay IS '法定休出手当';
COMMENT ON COLUMN cod_payment.night_in_pay IS '時間内深夜勤務手当';
COMMENT ON COLUMN cod_payment.night_out_pay IS '時間外深夜勤務手当';
COMMENT ON COLUMN cod_payment.night_united_pay IS '合算残業深夜勤務手当';
COMMENT ON COLUMN cod_payment.night_legal_pay IS '法定休出深夜勤務手当';
COMMENT ON COLUMN cod_payment.exceed_pay_total IS '超過勤務手当';
COMMENT ON COLUMN cod_payment.trans_a_expense IS '交通費A';
COMMENT ON COLUMN cod_payment.trans_b_expense IS '交通費B';
COMMENT ON COLUMN cod_payment.trans_c_expense IS '交通費C';
COMMENT ON COLUMN cod_payment.trans_z_expense IS '交通費その他';
COMMENT ON COLUMN cod_payment.trans_total IS '交通費支給額';
COMMENT ON COLUMN cod_payment.pay_total IS '支給額合計';
COMMENT ON COLUMN cod_payment.standard_pay IS '標準報酬月額';
COMMENT ON COLUMN cod_payment.health_ins_com IS '健康保険料(事業主負担)';
COMMENT ON COLUMN cod_payment.nurse_ins_com IS '介護保険料(事業主負担)';
COMMENT ON COLUMN cod_payment.welfare_ins_com IS '厚生年金保険料(事業主負担)';
COMMENT ON COLUMN cod_payment.child_care_fund IS '児童手当拠出金';
COMMENT ON COLUMN cod_payment.employ_ins IS '雇用保険料';
COMMENT ON COLUMN cod_payment.workmens_ins IS '労災保険料';
COMMENT ON COLUMN cod_payment.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_payment.insert_date IS '登録日';
COMMENT ON COLUMN cod_payment.insert_user IS '登録者';
COMMENT ON COLUMN cod_payment.update_date IS '更新日';
COMMENT ON COLUMN cod_payment.update_user IS '更新者';

CREATE TABLE cod_standing_attendance
(
  cod_standing_attendance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  work_date date NOT NULL,
  work_sequence_no integer NOT NULL DEFAULT 0,
  standing_code character varying(21) NOT NULL DEFAULT '',
  start_time timestamp without time zone,
  end_time timestamp without time zone,
  work_time integer NOT NULL DEFAULT 0,
  rest_time integer NOT NULL DEFAULT 0,
  overtime_in integer NOT NULL DEFAULT 0,
  overtime_out integer NOT NULL DEFAULT 0,
  united_overtime integer NOT NULL DEFAULT 0,
  late_night_time integer NOT NULL DEFAULT 0,
  late_night_normal integer NOT NULL DEFAULT 0,
  late_night_in integer NOT NULL DEFAULT 0,
  late_night_out integer NOT NULL DEFAULT 0,
  late_night_united integer NOT NULL DEFAULT 0,
  late_night_legal integer NOT NULL DEFAULT 0,
  legal_work_time integer NOT NULL DEFAULT 0,
  standing_comment text NOT NULL DEFAULT '',
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_standing_attendance_pkey PRIMARY KEY (cod_standing_attendance_id)
)
;
COMMENT ON TABLE cod_standing_attendance IS '身分勤怠データ';
COMMENT ON COLUMN cod_standing_attendance.cod_standing_attendance_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_standing_attendance.personal_id IS '個人ID';
COMMENT ON COLUMN cod_standing_attendance.work_date IS '勤務日';
COMMENT ON COLUMN cod_standing_attendance.work_sequence_no IS '勤務連番';
COMMENT ON COLUMN cod_standing_attendance.standing_code IS '身分コード';
COMMENT ON COLUMN cod_standing_attendance.start_time IS '始業時刻';
COMMENT ON COLUMN cod_standing_attendance.end_time IS '終業時刻';
COMMENT ON COLUMN cod_standing_attendance.work_time IS '勤務時間';
COMMENT ON COLUMN cod_standing_attendance.rest_time IS '休憩時間';
COMMENT ON COLUMN cod_standing_attendance.overtime_in IS '法定内残業時間';
COMMENT ON COLUMN cod_standing_attendance.overtime_out IS '法定外残業時間';
COMMENT ON COLUMN cod_standing_attendance.united_overtime IS '合算残業時間';
COMMENT ON COLUMN cod_standing_attendance.late_night_time IS '深夜勤務時間';
COMMENT ON COLUMN cod_standing_attendance.late_night_normal IS '深夜勤務時間(通常勤務)';
COMMENT ON COLUMN cod_standing_attendance.late_night_in IS '深夜勤務時間(法定内残業)';
COMMENT ON COLUMN cod_standing_attendance.late_night_out IS '深夜勤務時間(法定外残業)';
COMMENT ON COLUMN cod_standing_attendance.late_night_united IS '深夜勤務時間(合算残業)';
COMMENT ON COLUMN cod_standing_attendance.late_night_legal IS '深夜勤務時間(法定休日)';
COMMENT ON COLUMN cod_standing_attendance.legal_work_time IS '法定休日勤務時間';
COMMENT ON COLUMN cod_standing_attendance.standing_comment IS '身分コメント';
COMMENT ON COLUMN cod_standing_attendance.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_standing_attendance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_standing_attendance.insert_date IS '登録日';
COMMENT ON COLUMN cod_standing_attendance.insert_user IS '登録者';
COMMENT ON COLUMN cod_standing_attendance.update_date IS '更新日';
COMMENT ON COLUMN cod_standing_attendance.update_user IS '更新者';

CREATE TABLE cot_standing_attendance_time
(
  cot_standing_attendance_time_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  work_date date NOT NULL,
  work_sequence_no integer NOT NULL DEFAULT 0,
  time_item_type character varying(10) NOT NULL DEFAULT '',
  start_time timestamp without time zone,
  end_time timestamp without time zone,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cot_standing_attendance_time_pkey PRIMARY KEY (cot_standing_attendance_time_id)
)
;
COMMENT ON TABLE cot_standing_attendance_time IS '勤怠時刻情報';
COMMENT ON COLUMN cot_standing_attendance_time.cot_standing_attendance_time_id IS 'レコード識別ID';
COMMENT ON COLUMN cot_standing_attendance_time.personal_id IS '個人ID';
COMMENT ON COLUMN cot_standing_attendance_time.work_date IS '勤務日';
COMMENT ON COLUMN cot_standing_attendance_time.work_sequence_no IS '勤務連番';
COMMENT ON COLUMN cot_standing_attendance_time.time_item_type IS '時刻項目区分';
COMMENT ON COLUMN cot_standing_attendance_time.start_time IS '開始時刻';
COMMENT ON COLUMN cot_standing_attendance_time.end_time IS '終了時刻';
COMMENT ON COLUMN cot_standing_attendance_time.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cot_standing_attendance_time.insert_date IS '登録日';
COMMENT ON COLUMN cot_standing_attendance_time.insert_user IS '登録者';
COMMENT ON COLUMN cot_standing_attendance_time.update_date IS '更新日';
COMMENT ON COLUMN cot_standing_attendance_time.update_user IS '更新者';

CREATE TABLE cod_college_holiday
(
  cod_college_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  work_date date NOT NULL,
  standing_code character varying(21) NOT NULL DEFAULT '',
  holiday_item_type character varying(10) NOT NULL DEFAULT '',
  holiday_time integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_college_holiday_pkey PRIMARY KEY (cod_college_holiday_id)
)
;
COMMENT ON TABLE cod_college_holiday IS '複数身分休暇データ';
COMMENT ON COLUMN cod_college_holiday.cod_college_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_college_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN cod_college_holiday.work_date IS '勤務日';
COMMENT ON COLUMN cod_college_holiday.standing_code IS '身分コード';
COMMENT ON COLUMN cod_college_holiday.holiday_item_type IS '休暇項目区分';
COMMENT ON COLUMN cod_college_holiday.holiday_time IS '休暇時間';
COMMENT ON COLUMN cod_college_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_college_holiday.insert_date IS '登録日';
COMMENT ON COLUMN cod_college_holiday.insert_user IS '登録者';
COMMENT ON COLUMN cod_college_holiday.update_date IS '更新日';
COMMENT ON COLUMN cod_college_holiday.update_user IS '更新者';

CREATE TABLE cod_total_college
(
  cod_total_college_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  calculation_date date NOT NULL,
  normal_work integer NOT NULL DEFAULT 0,
  work_on_prescribed_holiday integer NOT NULL DEFAULT 0,
  work_on_legal_holiday integer NOT NULL DEFAULT 0,
  paid_holiday integer NOT NULL DEFAULT 0,
  transportation_expenses_a_work integer NOT NULL DEFAULT 0,
  transportation_expenses_b_work integer NOT NULL DEFAULT 0,
  transportation_expenses_c_work integer NOT NULL DEFAULT 0,
  transportation_expenses_z_work integer NOT NULL DEFAULT 0,
  transportation_expenses_amount_work integer NOT NULL DEFAULT 0,
  transportation_expenses_a_holiday integer NOT NULL DEFAULT 0,
  transportation_expenses_b_holiday integer NOT NULL DEFAULT 0,
  transportation_expenses_c_holiday integer NOT NULL DEFAULT 0,
  transportation_expenses_z_holiday integer NOT NULL DEFAULT 0,
  transportation_expenses_amount_holiday integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_total_college_pkey PRIMARY KEY (cod_total_college_id)
)
;
COMMENT ON TABLE cod_total_college IS '複数身分勤怠集計データ';
COMMENT ON COLUMN cod_total_college.cod_total_college_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_total_college.personal_id IS '個人ID';
COMMENT ON COLUMN cod_total_college.calculation_year IS '年';
COMMENT ON COLUMN cod_total_college.calculation_month IS '月';
COMMENT ON COLUMN cod_total_college.calculation_date IS '集計日';
COMMENT ON COLUMN cod_total_college.normal_work IS '通常出勤回数';
COMMENT ON COLUMN cod_total_college.work_on_prescribed_holiday IS '所定休日出勤回数';
COMMENT ON COLUMN cod_total_college.work_on_legal_holiday IS '法定休日出勤回数';
COMMENT ON COLUMN cod_total_college.paid_holiday IS '有給休暇回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_a_work IS '交通費A勤務回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_b_work IS '交通費B勤務回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_c_work IS '交通費C勤務回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_z_work IS '交通費Z勤務回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_amount_work IS '交通費勤務金額';
COMMENT ON COLUMN cod_total_college.transportation_expenses_a_holiday IS '交通費A有給休暇回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_b_holiday IS '交通費B有給休暇回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_c_holiday IS '交通費C有給休暇回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_z_holiday IS '交通費Z有給休暇回数';
COMMENT ON COLUMN cod_total_college.transportation_expenses_amount_holiday IS '交通費有給休暇金額';
COMMENT ON COLUMN cod_total_college.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_total_college.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_total_college.insert_date IS '登録日';
COMMENT ON COLUMN cod_total_college.insert_user IS '登録者';
COMMENT ON COLUMN cod_total_college.update_date IS '更新日';
COMMENT ON COLUMN cod_total_college.update_user IS '更新者';

CREATE TABLE cod_total_standing
(
  cod_total_standing_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  calculation_date date NOT NULL,
  standing_code character varying(21) NOT NULL DEFAULT '',
  normal_work integer NOT NULL DEFAULT 0,
  work_on_prescribed_holiday integer NOT NULL DEFAULT 0,
  work_on_legal_holiday integer NOT NULL DEFAULT 0,
  paid_holiday integer NOT NULL DEFAULT 0,
  work_time integer NOT NULL DEFAULT 0,
  work_time_pay integer NOT NULL DEFAULT 0,
  rest_time integer NOT NULL DEFAULT 0,
  overtime_in integer NOT NULL DEFAULT 0,
  overtime_out integer NOT NULL DEFAULT 0,
  overtime_out_pay integer NOT NULL DEFAULT 0,
  overtime_united integer NOT NULL DEFAULT 0, 
  overtime_united_pay integer NOT NULL DEFAULT 0,
  night_time integer NOT NULL DEFAULT 0,
  night_normal integer NOT NULL DEFAULT 0,
  night_in integer NOT NULL DEFAULT 0,
  night_in_pay integer NOT NULL DEFAULT 0,
  night_out integer NOT NULL DEFAULT 0,
  night_united integer NOT NULL DEFAULT 0,
  night_legal integer NOT NULL DEFAULT 0,
  legal_work_time integer NOT NULL DEFAULT 0,
  legal_work_time_pay integer NOT NULL DEFAULT 0,
  paid_holiday_time integer NOT NULL DEFAULT 0,
  work_time_pay_forty integer NOT NULL DEFAULT 0,
  overtime_in_forty integer NOT NULL DEFAULT 0,
  overtime_out_forty integer NOT NULL DEFAULT 0,
  overtime_out_pay_forty integer NOT NULL DEFAULT 0,
  overtime_united_forty integer NOT NULL DEFAULT 0,
  overtime_united_pay_forty integer NOT NULL DEFAULT 0,
  night_normal_forty integer NOT NULL DEFAULT 0,
  night_in_forty integer NOT NULL DEFAULT 0,
  night_in_pay_forty integer NOT NULL DEFAULT 0,
  night_out_forty integer NOT NULL DEFAULT 0,
  night_united_forty integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_total_standing_pkey PRIMARY KEY (cod_total_standing_id)
)
;
COMMENT ON TABLE cod_total_standing IS '身分勤怠集計データ';
COMMENT ON COLUMN cod_total_standing.cod_total_standing_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_total_standing.personal_id IS '個人ID';
COMMENT ON COLUMN cod_total_standing.calculation_year IS '年';
COMMENT ON COLUMN cod_total_standing.calculation_month IS '月';
COMMENT ON COLUMN cod_total_standing.calculation_date IS '集計日';
COMMENT ON COLUMN cod_total_standing.standing_code IS '身分コード';
COMMENT ON COLUMN cod_total_standing.normal_work IS '通常出勤回数';
COMMENT ON COLUMN cod_total_standing.work_on_prescribed_holiday IS '所定休日出勤回数';
COMMENT ON COLUMN cod_total_standing.work_on_legal_holiday IS '法定休日出勤回数';
COMMENT ON COLUMN cod_total_standing.paid_holiday IS '有給休暇回数';
COMMENT ON COLUMN cod_total_standing.work_time IS '勤務時間';
COMMENT ON COLUMN cod_total_standing.work_time_pay IS '勤務時間(勤務時間-割増対象)';
COMMENT ON COLUMN cod_total_standing.rest_time IS '休憩時間';
COMMENT ON COLUMN cod_total_standing.overtime_in IS '法定内残業時間';
COMMENT ON COLUMN cod_total_standing.overtime_out IS '法定外残業時間';
COMMENT ON COLUMN cod_total_standing.overtime_out_pay IS '法定外残業時間(外残-深夜外残)';
COMMENT ON COLUMN cod_total_standing.overtime_united IS '合算残業時間';
COMMENT ON COLUMN cod_total_standing.overtime_united_pay IS '合算残業時間(合残-深夜合残)';
COMMENT ON COLUMN cod_total_standing.night_time IS '深夜勤務時間';
COMMENT ON COLUMN cod_total_standing.night_normal IS '深夜勤務時間(通常勤務)';
COMMENT ON COLUMN cod_total_standing.night_in IS '深夜勤務時間(法定内残業)';
COMMENT ON COLUMN cod_total_standing.night_in_pay IS '深夜勤務時間(深夜通常+深夜内残)';
COMMENT ON COLUMN cod_total_standing.night_out IS '深夜勤務時間(法定外残業)';
COMMENT ON COLUMN cod_total_standing.night_united IS '深夜勤務時間(合算残業)';
COMMENT ON COLUMN cod_total_standing.night_legal IS '深夜勤務時間(法定休日)';
COMMENT ON COLUMN cod_total_standing.legal_work_time IS '法定休日勤務時間';
COMMENT ON COLUMN cod_total_standing.legal_work_time_pay IS '法定休日勤務時間(法休-深夜法休)';
COMMENT ON COLUMN cod_total_standing.paid_holiday_time IS '有給休暇時間';
COMMENT ON COLUMN cod_total_standing.work_time_pay_forty IS '勤務時間(勤務時間-割増対象)(週40)';
COMMENT ON COLUMN cod_total_standing.overtime_in_forty IS '法定内残業時間(週40)';
COMMENT ON COLUMN cod_total_standing.overtime_out_forty IS '法定外残業時間(週40)';
COMMENT ON COLUMN cod_total_standing.overtime_out_pay_forty IS '法定外残業時間(外残-深夜外残)(週40)';
COMMENT ON COLUMN cod_total_standing.overtime_united_forty IS '合算残業時間(週40)';
COMMENT ON COLUMN cod_total_standing.overtime_united_pay_forty IS '合算残業時間(合残-深夜合残)(週40)';
COMMENT ON COLUMN cod_total_standing.night_normal_forty IS '深夜勤務時間(通常勤務)(週40)';
COMMENT ON COLUMN cod_total_standing.night_in_forty IS '深夜勤務時間(法定内残業)(週40)';
COMMENT ON COLUMN cod_total_standing.night_in_pay_forty IS '深夜勤務時間(深夜通常+深夜内残)(週40)';
COMMENT ON COLUMN cod_total_standing.night_out_forty IS '深夜勤務時間(法定外残業)(週40)';
COMMENT ON COLUMN cod_total_standing.night_united_forty IS '深夜勤務時間(合算残業)(週40)';
COMMENT ON COLUMN cod_total_standing.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_total_standing.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_total_standing.insert_date IS '登録日';
COMMENT ON COLUMN cod_total_standing.insert_user IS '登録者';
COMMENT ON COLUMN cod_total_standing.update_date IS '更新日';
COMMENT ON COLUMN cod_total_standing.update_user IS '更新者';

CREATE TABLE com_college_setting
(
  com_college_setting_id bigint NOT NULL DEFAULT 0,
  college_setting_type character varying(30) NOT NULL DEFAULT '',
  college_setting_code character varying(30) NOT NULL DEFAULT '',
  activate_date date NOT NULL,
  college_setting_value text NOT NULL DEFAULT '',
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL,
  CONSTRAINT com_college_setting_pkey PRIMARY KEY (com_college_setting_id)
);
COMMENT ON TABLE com_college_setting IS '複数身分設定マスタ';
COMMENT ON COLUMN com_college_setting.com_college_setting_id IS 'レコード識別ID';
COMMENT ON COLUMN com_college_setting.college_setting_type IS '複数身分設定区分';
COMMENT ON COLUMN com_college_setting.college_setting_code IS '複数身分設定コード';
COMMENT ON COLUMN com_college_setting.activate_date IS '有効日';
COMMENT ON COLUMN com_college_setting.college_setting_value IS '複数身分設定値';
COMMENT ON COLUMN com_college_setting.delete_flag IS '削除フラグ';
COMMENT ON COLUMN com_college_setting.insert_date IS '登録日';
COMMENT ON COLUMN com_college_setting.insert_user IS '登録者';
COMMENT ON COLUMN com_college_setting.update_date IS '更新日';
COMMENT ON COLUMN com_college_setting.update_user IS '更新者';

CREATE TABLE cod_address_request
(
  cod_address_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  move_date date NOT NULL,
  move_reason character varying(10) NOT NULL DEFAULT ''::character varying,
  householder_name character varying(30) NOT NULL DEFAULT ''::character varying,
  relation character varying(30) NOT NULL DEFAULT ''::character varying,
  address_differ integer NOT NULL DEFAULT 0,
  phone_change integer NOT NULL DEFAULT 0,
  trans_change integer NOT NULL DEFAULT 0,
  address_secret integer NOT NULL DEFAULT 0,
  phone_secret integer NOT NULL DEFAULT 0,
  extension character varying(30) NOT NULL DEFAULT ''::character varying,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT cod_address_request_pkey PRIMARY KEY (cod_address_request_id)
)
;
COMMENT ON TABLE cod_address_request IS '住所変更申請';
COMMENT ON COLUMN cod_address_request.cod_address_request_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_address_request.personal_Id IS '個人ID';
COMMENT ON COLUMN cod_address_request.move_date IS '転居日付';
COMMENT ON COLUMN cod_address_request.move_reason IS '理由';
COMMENT ON COLUMN cod_address_request.householder_name IS '世帯主氏名';
COMMENT ON COLUMN cod_address_request.relation IS '続柄';
COMMENT ON COLUMN cod_address_request.address_differ IS '住民票上住所';
COMMENT ON COLUMN cod_address_request.phone_change IS '転居に伴う電話番号の変更有';
COMMENT ON COLUMN cod_address_request.trans_change IS '転居に伴う通勤交通費の変更有';
COMMENT ON COLUMN cod_address_request.address_secret IS '住所の開示不可';
COMMENT ON COLUMN cod_address_request.phone_secret IS '電話番号の開示不可';
COMMENT ON COLUMN cod_address_request.extension IS '内線';
COMMENT ON COLUMN cod_address_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_address_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_address_request.insert_date IS '登録日';
COMMENT ON COLUMN cod_address_request.insert_user IS '登録者';
COMMENT ON COLUMN cod_address_request.update_date IS '更新日';
COMMENT ON COLUMN cod_address_request.update_user IS '更新者';

CREATE TABLE cod_prospects_total_standing
(
  cod_prospects_total_standing_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  calculation_date date NOT NULL,
  standing_code character varying(21) NOT NULL DEFAULT '',
  normal_work integer NOT NULL DEFAULT 0,
  work_on_prescribed_holiday integer NOT NULL DEFAULT 0,
  work_on_legal_holiday integer NOT NULL DEFAULT 0,
  paid_holiday integer NOT NULL DEFAULT 0,
  work_time integer NOT NULL DEFAULT 0,
  work_time_pay integer NOT NULL DEFAULT 0,
  rest_time integer NOT NULL DEFAULT 0,
  overtime_in integer NOT NULL DEFAULT 0,
  overtime_out integer NOT NULL DEFAULT 0,
  overtime_out_pay integer NOT NULL DEFAULT 0,
  overtime_united integer NOT NULL DEFAULT 0, 
  overtime_united_pay integer NOT NULL DEFAULT 0,
  night_time integer NOT NULL DEFAULT 0,
  night_normal integer NOT NULL DEFAULT 0,
  night_in integer NOT NULL DEFAULT 0,
  night_in_pay integer NOT NULL DEFAULT 0,
  night_out integer NOT NULL DEFAULT 0,
  night_united integer NOT NULL DEFAULT 0,
  night_legal integer NOT NULL DEFAULT 0,
  legal_work_time integer NOT NULL DEFAULT 0,
  legal_work_time_pay integer NOT NULL DEFAULT 0,
  paid_holiday_time integer NOT NULL DEFAULT 0,
  work_time_pay_forty integer NOT NULL DEFAULT 0,
  overtime_in_forty integer NOT NULL DEFAULT 0,
  overtime_out_forty integer NOT NULL DEFAULT 0,
  overtime_out_pay_forty integer NOT NULL DEFAULT 0,
  overtime_united_forty integer NOT NULL DEFAULT 0,
  overtime_united_pay_forty integer NOT NULL DEFAULT 0,
  night_normal_forty integer NOT NULL DEFAULT 0,
  night_in_forty integer NOT NULL DEFAULT 0,
  night_in_pay_forty integer NOT NULL DEFAULT 0,
  night_out_forty integer NOT NULL DEFAULT 0,
  night_united_forty integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_prospects_total_standing_pkey PRIMARY KEY (cod_prospects_total_standing_id)
)
;
COMMENT ON TABLE cod_prospects_total_standing IS '身分勤怠データ(見込み)';
COMMENT ON COLUMN cod_prospects_total_standing.cod_prospects_total_standing_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_prospects_total_standing.personal_id IS '個人ID';
COMMENT ON COLUMN cod_prospects_total_standing.calculation_year IS '年';
COMMENT ON COLUMN cod_prospects_total_standing.calculation_month IS '月';
COMMENT ON COLUMN cod_prospects_total_standing.calculation_date IS '集計日';
COMMENT ON COLUMN cod_prospects_total_standing.standing_code IS '身分コード';
COMMENT ON COLUMN cod_prospects_total_standing.normal_work IS '通常出勤回数';
COMMENT ON COLUMN cod_prospects_total_standing.work_on_prescribed_holiday IS '所定休日出勤回数';
COMMENT ON COLUMN cod_prospects_total_standing.work_on_legal_holiday IS '法定休日出勤回数';
COMMENT ON COLUMN cod_prospects_total_standing.paid_holiday IS '有給休暇回数';
COMMENT ON COLUMN cod_prospects_total_standing.work_time IS '勤務時間';
COMMENT ON COLUMN cod_prospects_total_standing.work_time_pay IS '勤務時間(勤務時間-割増対象)';
COMMENT ON COLUMN cod_prospects_total_standing.rest_time IS '休憩時間';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_in IS '法定内残業時間';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_out IS '法定外残業時間';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_out_pay IS '法定外残業時間(外残-深夜外残)';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_united IS '合算残業時間';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_united_pay IS '合算残業時間(合残-深夜合残)';
COMMENT ON COLUMN cod_prospects_total_standing.night_time IS '深夜勤務時間';
COMMENT ON COLUMN cod_prospects_total_standing.night_normal IS '深夜勤務時間(通常勤務)';
COMMENT ON COLUMN cod_prospects_total_standing.night_in IS '深夜勤務時間(法定内残業)';
COMMENT ON COLUMN cod_prospects_total_standing.night_in_pay IS '深夜勤務時間(深夜通常+深夜内残)';
COMMENT ON COLUMN cod_prospects_total_standing.night_out IS '深夜勤務時間(法定外残業)';
COMMENT ON COLUMN cod_prospects_total_standing.night_united IS '深夜勤務時間(合算残業)';
COMMENT ON COLUMN cod_prospects_total_standing.night_legal IS '深夜勤務時間(法定休日)';
COMMENT ON COLUMN cod_prospects_total_standing.legal_work_time IS '法定休日勤務時間';
COMMENT ON COLUMN cod_prospects_total_standing.legal_work_time_pay IS '法定休日勤務時間(法休-深夜法休)';
COMMENT ON COLUMN cod_prospects_total_standing.paid_holiday_time IS '有給休暇時間';
COMMENT ON COLUMN cod_prospects_total_standing.work_time_pay_forty IS '勤務時間(勤務時間-割増対象)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_in_forty IS '法定内残業時間(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_out_forty IS '法定外残業時間(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_out_pay_forty IS '法定外残業時間(外残-深夜外残)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_united_forty IS '合算残業時間(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.overtime_united_pay_forty IS '合算残業時間(合残-深夜合残)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.night_normal_forty IS '深夜勤務時間(通常勤務)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.night_in_forty IS '深夜勤務時間(法定内残業)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.night_in_pay_forty IS '深夜勤務時間(深夜通常+深夜内残)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.night_out_forty IS '深夜勤務時間(法定外残業)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.night_united_forty IS '深夜勤務時間(合算残業)(週40)';
COMMENT ON COLUMN cod_prospects_total_standing.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_prospects_total_standing.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_prospects_total_standing.insert_date IS '登録日';
COMMENT ON COLUMN cod_prospects_total_standing.insert_user IS '登録者';
COMMENT ON COLUMN cod_prospects_total_standing.update_date IS '更新日';
COMMENT ON COLUMN cod_prospects_total_standing.update_user IS '更新者';

CREATE TABLE cod_prospects_college_attendance
(
  cod_prospects_college_attendance_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  work_date date NOT NULL, -- 勤務日
  work_type_code character varying(20) NOT NULL DEFAULT ''::character varying, -- 勤務形態コード
  paid_holiday_grant_date date, -- 有給休暇付与日
  applicant_comment text NOT NULL DEFAULT ''::text, -- 申請者コメント
  transportation_expenses_route character varying(1) NOT NULL DEFAULT ''::character varying, -- 交通費ルート
  transportation_expenses_amount integer NOT NULL DEFAULT 0, -- 交通費金額
  workflow bigint NOT NULL DEFAULT 0, -- ワークフロー番号
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT cod_prospects_college_attendance_pkey PRIMARY KEY (cod_prospects_college_attendance_id)
)
;
COMMENT ON TABLE cod_prospects_college_attendance IS '複数身分勤怠データ(見込)';
COMMENT ON COLUMN cod_prospects_college_attendance.cod_prospects_college_attendance_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_prospects_college_attendance.personal_id IS '個人ID';
COMMENT ON COLUMN cod_prospects_college_attendance.work_date IS '勤務日';
COMMENT ON COLUMN cod_prospects_college_attendance.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN cod_prospects_college_attendance.paid_holiday_grant_date IS '有給休暇付与日';
COMMENT ON COLUMN cod_prospects_college_attendance.applicant_comment IS '申請者コメント';
COMMENT ON COLUMN cod_prospects_college_attendance.transportation_expenses_route IS '交通費ルート';
COMMENT ON COLUMN cod_prospects_college_attendance.transportation_expenses_amount IS '交通費金額';
COMMENT ON COLUMN cod_prospects_college_attendance.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_prospects_college_attendance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_prospects_college_attendance.insert_date IS '登録日';
COMMENT ON COLUMN cod_prospects_college_attendance.insert_user IS '登録者';
COMMENT ON COLUMN cod_prospects_college_attendance.update_date IS '更新日';
COMMENT ON COLUMN cod_prospects_college_attendance.update_user IS '更新者';

CREATE TABLE cod_prospects_standing_attendance
(
  cod_prospects_standing_attendance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  work_date date NOT NULL,
  work_sequence_no integer NOT NULL DEFAULT 0,
  standing_code character varying(21) NOT NULL DEFAULT '',
  start_time timestamp without time zone,
  end_time timestamp without time zone,
  work_time integer NOT NULL DEFAULT 0,
  rest_time integer NOT NULL DEFAULT 0,
  overtime_in integer NOT NULL DEFAULT 0,
  overtime_out integer NOT NULL DEFAULT 0,
  united_overtime integer NOT NULL DEFAULT 0,
  late_night_time integer NOT NULL DEFAULT 0,
  late_night_normal integer NOT NULL DEFAULT 0,
  late_night_in integer NOT NULL DEFAULT 0,
  late_night_out integer NOT NULL DEFAULT 0,
  late_night_united integer NOT NULL DEFAULT 0,
  late_night_legal integer NOT NULL DEFAULT 0,
  legal_work_time integer NOT NULL DEFAULT 0,
  standing_comment text NOT NULL DEFAULT '',
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_prospects_standing_attendance_pkey PRIMARY KEY (cod_prospects_standing_attendance_id)
)
;
COMMENT ON TABLE cod_prospects_standing_attendance IS '身分勤怠データ(見込)';
COMMENT ON COLUMN cod_prospects_standing_attendance.cod_prospects_standing_attendance_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_prospects_standing_attendance.personal_id IS '個人ID';
COMMENT ON COLUMN cod_prospects_standing_attendance.work_date IS '勤務日';
COMMENT ON COLUMN cod_prospects_standing_attendance.work_sequence_no IS '勤務連番';
COMMENT ON COLUMN cod_prospects_standing_attendance.standing_code IS '身分コード';
COMMENT ON COLUMN cod_prospects_standing_attendance.start_time IS '始業時刻';
COMMENT ON COLUMN cod_prospects_standing_attendance.end_time IS '終業時刻';
COMMENT ON COLUMN cod_prospects_standing_attendance.work_time IS '勤務時間';
COMMENT ON COLUMN cod_prospects_standing_attendance.rest_time IS '休憩時間';
COMMENT ON COLUMN cod_prospects_standing_attendance.overtime_in IS '法定内残業時間';
COMMENT ON COLUMN cod_prospects_standing_attendance.overtime_out IS '法定外残業時間';
COMMENT ON COLUMN cod_prospects_standing_attendance.united_overtime IS '合算残業時間';
COMMENT ON COLUMN cod_prospects_standing_attendance.late_night_time IS '深夜勤務時間';
COMMENT ON COLUMN cod_prospects_standing_attendance.late_night_normal IS '深夜勤務時間(通常勤務)';
COMMENT ON COLUMN cod_prospects_standing_attendance.late_night_in IS '深夜勤務時間(法定内残業)';
COMMENT ON COLUMN cod_prospects_standing_attendance.late_night_out IS '深夜勤務時間(法定外残業)';
COMMENT ON COLUMN cod_prospects_standing_attendance.late_night_united IS '深夜勤務時間(合算残業)';
COMMENT ON COLUMN cod_prospects_standing_attendance.late_night_legal IS '深夜勤務時間(法定休日)';
COMMENT ON COLUMN cod_prospects_standing_attendance.legal_work_time IS '法定休日勤務時間';
COMMENT ON COLUMN cod_prospects_standing_attendance.standing_comment IS '身分コメント';
COMMENT ON COLUMN cod_prospects_standing_attendance.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_prospects_standing_attendance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_prospects_standing_attendance.insert_date IS '登録日';
COMMENT ON COLUMN cod_prospects_standing_attendance.insert_user IS '登録者';
COMMENT ON COLUMN cod_prospects_standing_attendance.update_date IS '更新日';
COMMENT ON COLUMN cod_prospects_standing_attendance.update_user IS '更新者';

CREATE TABLE cod_prospects_total_college
(
  cod_prospects_total_college_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  calculation_year integer NOT NULL DEFAULT 0, -- 年
  calculation_month integer NOT NULL DEFAULT 0, -- 月
  calculation_date date NOT NULL, -- 集計日
  normal_work integer NOT NULL DEFAULT 0, -- 通常出勤回数
  work_on_prescribed_holiday integer NOT NULL DEFAULT 0, -- 所定休日出勤回数
  work_on_legal_holiday integer NOT NULL DEFAULT 0, -- 法定休日出勤回数
  paid_holiday integer NOT NULL DEFAULT 0, -- 有給休暇回数
  transportation_expenses_a_work integer NOT NULL DEFAULT 0, -- 交通費A勤務回数
  transportation_expenses_b_work integer NOT NULL DEFAULT 0, -- 交通費B勤務回数
  transportation_expenses_c_work integer NOT NULL DEFAULT 0, -- 交通費C勤務回数
  transportation_expenses_z_work integer NOT NULL DEFAULT 0, -- 交通費Z勤務回数
  transportation_expenses_amount_work integer NOT NULL DEFAULT 0, -- 交通費勤務金額
  transportation_expenses_a_holiday integer NOT NULL DEFAULT 0, -- 交通費A有給休暇回数
  transportation_expenses_b_holiday integer NOT NULL DEFAULT 0, -- 交通費B有給休暇回数
  transportation_expenses_c_holiday integer NOT NULL DEFAULT 0, -- 交通費C有給休暇回数
  transportation_expenses_z_holiday integer NOT NULL DEFAULT 0, -- 交通費Z有給休暇回数
  transportation_expenses_amount_holiday integer NOT NULL DEFAULT 0, -- 交通費有給休暇金額
  workflow bigint NOT NULL DEFAULT 0, -- ワークフロー番号
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT cod_prospects_total_college_pkey PRIMARY KEY (cod_prospects_total_college_id)
)
;
COMMENT ON TABLE cod_prospects_total_college IS '複数身分勤怠集計データ(見込)';
COMMENT ON COLUMN cod_prospects_total_college.cod_prospects_total_college_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_prospects_total_college.personal_id IS '個人ID';
COMMENT ON COLUMN cod_prospects_total_college.calculation_year IS '年';
COMMENT ON COLUMN cod_prospects_total_college.calculation_month IS '月';
COMMENT ON COLUMN cod_prospects_total_college.calculation_date IS '集計日';
COMMENT ON COLUMN cod_prospects_total_college.normal_work IS '通常出勤回数';
COMMENT ON COLUMN cod_prospects_total_college.work_on_prescribed_holiday IS '所定休日出勤回数';
COMMENT ON COLUMN cod_prospects_total_college.work_on_legal_holiday IS '法定休日出勤回数';
COMMENT ON COLUMN cod_prospects_total_college.paid_holiday IS '有給休暇回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_a_work IS '交通費A勤務回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_b_work IS '交通費B勤務回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_c_work IS '交通費C勤務回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_z_work IS '交通費Z勤務回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_amount_work IS '交通費勤務金額';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_a_holiday IS '交通費A有給休暇回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_b_holiday IS '交通費B有給休暇回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_c_holiday IS '交通費C有給休暇回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_z_holiday IS '交通費Z有給休暇回数';
COMMENT ON COLUMN cod_prospects_total_college.transportation_expenses_amount_holiday IS '交通費有給休暇金額';
COMMENT ON COLUMN cod_prospects_total_college.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_prospects_total_college.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_prospects_total_college.insert_date IS '登録日';
COMMENT ON COLUMN cod_prospects_total_college.insert_user IS '登録者';
COMMENT ON COLUMN cod_prospects_total_college.update_date IS '更新日';
COMMENT ON COLUMN cod_prospects_total_college.update_user IS '更新者';

CREATE TABLE cod_prospects_college_holiday
(
  cod_prospects_college_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT '',
  work_date date NOT NULL,
  standing_code character varying(21) NOT NULL DEFAULT '',
  holiday_item_type character varying(10) NOT NULL DEFAULT '',
  holiday_time integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT '',
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT '',
  CONSTRAINT cod_prospects_college_holiday_pkey PRIMARY KEY (cod_prospects_college_holiday_id)
)
;
COMMENT ON TABLE cod_prospects_college_holiday IS '複数身分休暇データ(見込)';
COMMENT ON COLUMN cod_prospects_college_holiday.cod_prospects_college_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_prospects_college_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN cod_prospects_college_holiday.work_date IS '勤務日';
COMMENT ON COLUMN cod_prospects_college_holiday.standing_code IS '身分コード';
COMMENT ON COLUMN cod_prospects_college_holiday.holiday_item_type IS '休暇項目区分';
COMMENT ON COLUMN cod_prospects_college_holiday.holiday_time IS '休暇時間';
COMMENT ON COLUMN cod_prospects_college_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_prospects_college_holiday.insert_date IS '登録日';
COMMENT ON COLUMN cod_prospects_college_holiday.insert_user IS '登録者';
COMMENT ON COLUMN cod_prospects_college_holiday.update_date IS '更新日';
COMMENT ON COLUMN cod_prospects_college_holiday.update_user IS '更新者';

CREATE TABLE cot_prospects_standing_attendance_time
(
  cot_prospects_standing_attendance_time_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  work_date date NOT NULL, -- 勤務日
  work_sequence_no integer NOT NULL DEFAULT 0, -- 勤務連番
  time_item_type character varying(10) NOT NULL DEFAULT ''::character varying, -- 時刻項目区分
  start_time timestamp without time zone, -- 開始時刻
  end_time timestamp without time zone, -- 終了時刻
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT cot_prospects_standing_attendance_time_pkey PRIMARY KEY (cot_prospects_standing_attendance_time_id)
)
;
COMMENT ON TABLE cot_prospects_standing_attendance_time IS '勤怠時刻情報(見込)';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.cot_prospects_standing_attendance_time_id IS 'レコード識別ID';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.personal_id IS '個人ID';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.work_date IS '勤務日';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.work_sequence_no IS '勤務連番';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.time_item_type IS '時刻項目区分';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.start_time IS '開始時刻';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.end_time IS '終了時刻';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.insert_date IS '登録日';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.insert_user IS '登録者';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.update_date IS '更新日';
COMMENT ON COLUMN cot_prospects_standing_attendance_time.update_user IS '更新者';

CREATE TABLE cod_transportation_request
(
  cod_transportation_request_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  activate_date date NOT NULL, -- 有効日
  application_reason_type integer NOT NULL DEFAULT 0, -- 申請理由区分
  application_reason_comment character varying(20) NOT NULL DEFAULT ''::character varying, -- 申請理由
  calculation integer NOT NULL DEFAULT 0, -- 精算方法
  main_home_nearest_station character varying(30) NOT NULL DEFAULT ''::character varying, -- 自宅最寄り駅(主ルート)
  main_location_nearest_station	character varying(30) NOT NULL DEFAULT ''::character varying, -- 勤務地最寄り駅(主ルート)
  sub_1_home_nearest_station character varying(30) NOT NULL DEFAULT ''::character varying, -- 自宅最寄り駅(副ルート1)
  sub_1_location_nearest_station character varying(30) NOT NULL DEFAULT ''::character varying, -- 勤務地最寄り駅(副ルート1)
  sub_2_home_nearest_station character varying(30) NOT NULL DEFAULT ''::character varying, -- 自宅最寄り駅(副ルート2)
  sub_2_location_nearest_station character varying(30) NOT NULL DEFAULT ''::character varying, -- 勤務地最寄り駅(副ルート2)
  transportation_expenses_a_abbr character varying(6) NOT NULL DEFAULT ''::character varying, -- 交通費A略称
  transportation_expenses_b_abbr character varying(6) NOT NULL DEFAULT ''::character varying, -- 交通費B略称
  transportation_expenses_c_abbr character varying(6) NOT NULL DEFAULT ''::character varying, -- 交通費C略称
  transportation_remark text NOT NULL DEFAULT ''::text, -- 備考
  extension character varying(30) NOT NULL DEFAULT ''::character varying, -- 内線
  workflow bigint NOT NULL DEFAULT 0, -- ワークフロー番号
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT cod_transportation_request_pkey PRIMARY KEY (cod_transportation_request_id)
)
;
COMMENT ON TABLE cod_transportation_request IS '交通費申請';
COMMENT ON COLUMN cod_transportation_request.cod_transportation_request_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_transportation_request.personal_id IS '個人ID';
COMMENT ON COLUMN cod_transportation_request.activate_date IS '有効日';
COMMENT ON COLUMN cod_transportation_request.application_reason_type IS '申請理由区分';
COMMENT ON COLUMN cod_transportation_request.application_reason_comment IS '申請理由';
COMMENT ON COLUMN cod_transportation_request.calculation IS '精算方法';
COMMENT ON COLUMN cod_transportation_request.main_home_nearest_station IS '自宅最寄り駅(主ルート)';
COMMENT ON COLUMN cod_transportation_request.main_location_nearest_station IS '勤務地最寄り駅(主ルート)';
COMMENT ON COLUMN cod_transportation_request.sub_1_home_nearest_station IS '自宅最寄り駅(副ルート1)';
COMMENT ON COLUMN cod_transportation_request.sub_1_location_nearest_station IS '勤務地最寄り駅(副ルート1)';
COMMENT ON COLUMN cod_transportation_request.sub_2_home_nearest_station IS '自宅最寄り駅(副ルート2)';
COMMENT ON COLUMN cod_transportation_request.sub_2_location_nearest_station IS '勤務地最寄り駅(副ルート2)';
COMMENT ON COLUMN cod_transportation_request.transportation_expenses_a_abbr IS '交通費A略称';
COMMENT ON COLUMN cod_transportation_request.transportation_expenses_b_abbr IS '交通費B略称';
COMMENT ON COLUMN cod_transportation_request.transportation_expenses_c_abbr IS '交通費C略称';
COMMENT ON COLUMN cod_transportation_request.transportation_remark IS '備考';
COMMENT ON COLUMN cod_transportation_request.extension IS '内線';
COMMENT ON COLUMN cod_transportation_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_transportation_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_transportation_request.insert_date IS '登録日';
COMMENT ON COLUMN cod_transportation_request.insert_user IS '登録者';
COMMENT ON COLUMN cod_transportation_request.update_date IS '更新日';
COMMENT ON COLUMN cod_transportation_request.update_user IS '更新者';

CREATE TABLE coa_transportation_route
(
  coa_transportation_route_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  activate_date date NOT NULL, -- 有効日
  transportation_route_type character varying(10) NOT NULL DEFAULT ''::character varying, -- 交通順路区分
  row_id integer NOT NULL DEFAULT 0, -- 順路
  transportation integer NOT NULL DEFAULT 0, -- 交通機関
  departure_station character varying(30) NOT NULL DEFAULT ''::character varying, -- 出発駅
  purpose_station character varying(30) NOT NULL DEFAULT ''::character varying, -- 目的駅
  company_name character varying(30) NOT NULL DEFAULT ''::character varying, -- バス/鉄道会社名
  route_name character varying(30) NOT NULL DEFAULT ''::character varying, -- 路線名
  single_fare integer NOT NULL DEFAULT 0, -- 片道料金
  recurring_fee integer NOT NULL DEFAULT 0, -- 定期代
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT coa_transportation_route_pkey PRIMARY KEY (coa_transportation_route_id)
)
;
COMMENT ON TABLE coa_transportation_route IS '交通順路情報';
COMMENT ON COLUMN coa_transportation_route.coa_transportation_route_id IS 'レコード識別ID';
COMMENT ON COLUMN coa_transportation_route.personal_id IS '個人ID';
COMMENT ON COLUMN coa_transportation_route.activate_date IS '有効日';
COMMENT ON COLUMN coa_transportation_route.transportation_route_type IS '交通順路区分';
COMMENT ON COLUMN coa_transportation_route.row_id IS '順路';
COMMENT ON COLUMN coa_transportation_route.transportation IS '交通機関';
COMMENT ON COLUMN coa_transportation_route.departure_station IS '出発駅';
COMMENT ON COLUMN coa_transportation_route.purpose_station IS '目的駅';
COMMENT ON COLUMN coa_transportation_route.company_name IS 'バス/鉄道会社名';
COMMENT ON COLUMN coa_transportation_route.route_name IS '路線名';
COMMENT ON COLUMN coa_transportation_route.single_fare IS '片道料金';
COMMENT ON COLUMN coa_transportation_route.recurring_fee IS '定期代';
COMMENT ON COLUMN coa_transportation_route.delete_flag IS '削除フラグ';
COMMENT ON COLUMN coa_transportation_route.insert_date IS '登録日';
COMMENT ON COLUMN coa_transportation_route.insert_user IS '登録者';
COMMENT ON COLUMN coa_transportation_route.update_date IS '更新日';
COMMENT ON COLUMN coa_transportation_route.update_user IS '更新者';

CREATE TABLE coa_user_onetime_password
(
  coa_user_onetime_password_id bigint NOT NULL DEFAULT 0,
  user_id character varying(50) NOT NULL DEFAULT ''::character varying,
  issued_date date NOT NULL,
  onetime_password character varying(50) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT coa_user_onetime_password_pkey PRIMARY KEY (coa_user_onetime_password_id)
)
;
COMMENT ON TABLE coa_user_onetime_password IS 'ユーザワンタイムパスワード情報';
COMMENT ON COLUMN coa_user_onetime_password.coa_user_onetime_password_id IS 'レコード識別ID';
COMMENT ON COLUMN coa_user_onetime_password.user_id IS 'ユーザID';
COMMENT ON COLUMN coa_user_onetime_password.issued_date IS '発行日';
COMMENT ON COLUMN coa_user_onetime_password.onetime_password IS 'ワンタイムパスワード';
COMMENT ON COLUMN coa_user_onetime_password.delete_flag IS '削除フラグ';
COMMENT ON COLUMN coa_user_onetime_password.insert_date IS '登録日';
COMMENT ON COLUMN coa_user_onetime_password.insert_user IS '登録者';
COMMENT ON COLUMN coa_user_onetime_password.update_date IS '更新日';
COMMENT ON COLUMN coa_user_onetime_password.update_user IS '更新者';

CREATE TABLE cod_password_request
(
  cod_password_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  request_date date NOT NULL,
  registered_birth_date integer NOT NULL DEFAULT 0,
  input_birth_date date NOT NULL,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT cod_password_request_pkey PRIMARY KEY (cod_password_request_id)
)
;
COMMENT ON TABLE cod_password_request IS 'パスワード変更申請';
COMMENT ON COLUMN cod_password_request.cod_password_request_id IS 'レコード識別ID';
COMMENT ON COLUMN cod_password_request.personal_id IS '個人ID';
COMMENT ON COLUMN cod_password_request.request_date IS '申請日';
COMMENT ON COLUMN cod_password_request.registered_birth_date IS '生年月日登録';
COMMENT ON COLUMN cod_password_request.input_birth_date IS '入力生年月日';
COMMENT ON COLUMN cod_password_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN cod_password_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN cod_password_request.insert_date IS '登録日';
COMMENT ON COLUMN cod_password_request.insert_user IS '登録者';
COMMENT ON COLUMN cod_password_request.update_date IS '更新日';
COMMENT ON COLUMN cod_password_request.update_user IS '更新者';

CREATE SEQUENCE cod_transportation_request_id_seq;
CREATE SEQUENCE coa_transportation_route_id_seq;

CREATE SEQUENCE com_college_human_id_seq;
CREATE SEQUENCE com_fund_id_seq;
CREATE SEQUENCE com_standing_id_seq;
CREATE SEQUENCE com_standard_pay_id_seq;
CREATE SEQUENCE cod_adopt_request_id_seq;
CREATE SEQUENCE coa_adopt_item_id_seq;
CREATE SEQUENCE com_college_allocation_id_seq;
CREATE SEQUENCE cod_adopt_request_adopt_code_seq;
CREATE SEQUENCE com_standing_standing_code_seq;

CREATE SEQUENCE coa_standing_time_id_seq;
CREATE SEQUENCE cod_college_attendance_id_seq;
CREATE SEQUENCE cod_payment_id_seq;
CREATE SEQUENCE cod_standing_attendance_id_seq;
CREATE SEQUENCE cot_standing_attendance_time_id_seq;
CREATE SEQUENCE cod_college_holiday_id_seq;
CREATE SEQUENCE cod_total_college_id_seq;
CREATE SEQUENCE cod_total_standing_id_seq;
CREATE SEQUENCE com_college_setting_id_seq;
CREATE SEQUENCE cod_address_request_id_seq;

CREATE SEQUENCE cod_prospects_total_standing_id_seq;
CREATE SEQUENCE cod_prospects_college_attendance_id_seq;
CREATE SEQUENCE cod_prospects_standing_attendance_id_seq;
CREATE SEQUENCE cod_prospects_total_college_id_seq;
CREATE SEQUENCE cod_prospects_college_holiday_id_seq;
CREATE SEQUENCE cot_prospects_standing_attendance_time_id_seq;

CREATE SEQUENCE coa_user_onetime_password_id_seq;
CREATE SEQUENCE cod_password_request_id_seq;

CREATE INDEX com_college_human_index1 ON com_college_human(personal_id, activate_date);
CREATE INDEX com_college_human_index2 ON com_college_human(holiday_approval_route);
CREATE INDEX com_college_human_index3 ON com_college_human(united_monthly_approval_route);
CREATE INDEX com_fund_index1 ON com_fund(fund_code, activate_date);
CREATE INDEX com_standard_pay_index1 ON com_standard_pay(personal_id, calc_year, calc_month);
CREATE INDEX cod_adopt_request_index1 ON cod_adopt_request(standing_code, activate_date);
CREATE INDEX cod_adopt_request_index2 ON cod_adopt_request(workflow);
CREATE INDEX cod_adopt_request_index3 ON cod_adopt_request(create_personal_id);
CREATE INDEX coa_adopt_item_index1 ON coa_adopt_item(adopt_code);
CREATE INDEX com_college_allocation_index1 ON com_college_allocation(personal_id, activate_date);

CREATE INDEX com_standing_index1 ON com_standing(standing_code, activate_date, personal_id);
CREATE INDEX com_standing_index2 ON com_standing(daily_approval_route);
CREATE INDEX com_standing_index3 ON com_standing(monthly_approval_route);
CREATE INDEX coa_standing_time_index1 ON coa_standing_time(standing_code, activate_date);
CREATE INDEX cod_college_attendance_index1 ON cod_college_attendance(personal_id, work_date);
CREATE INDEX cod_payment_index1 ON cod_payment(personal_id, calc_year, calc_month, standing_code);
CREATE INDEX cod_standing_attendance_index1 ON cod_standing_attendance(personal_id, work_date);
CREATE INDEX cot_standing_attendance_time_index1 ON cot_standing_attendance_time(personal_id, work_date);
CREATE INDEX cod_college_holiday_index1 ON cod_college_holiday(personal_id, work_date);
CREATE INDEX cod_total_college_index1 ON cod_total_college(personal_id, calculation_year, calculation_month);
CREATE INDEX cod_total_standing_index1 ON cod_total_standing(personal_id, calculation_year, calculation_month);
CREATE INDEX cod_address_request_index1 ON cod_address_request(personal_id, move_date);

CREATE INDEX cod_prospects_total_standing_index1 ON cod_prospects_total_standing(personal_id, calculation_year, calculation_month);
CREATE INDEX cod_prospects_college_attendance_index1 ON cod_prospects_college_attendance(personal_id, work_date);
CREATE INDEX cod_prospects_standing_attendance_index1 ON cod_prospects_standing_attendance(personal_id, work_date);
CREATE INDEX cod_prospects_total_college_index1 ON cod_prospects_total_college(personal_id, calculation_year, calculation_month);
CREATE INDEX cod_prospects_college_holiday_index1 ON cod_prospects_college_holiday(personal_id, work_date);
CREATE INDEX cot_prospects_standing_attendance_time_index1 ON cot_prospects_standing_attendance_time(personal_id, work_date);

CREATE INDEX coa_user_onetime_password_index1 ON coa_user_onetime_password(user_id);
CREATE INDEX cod_password_request_index1 ON cod_password_request(personal_id, request_date);
