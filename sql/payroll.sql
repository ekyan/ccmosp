CREATE TABLE prd_adjustment_calc
(
 prd_adjustment_calc_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
 adjustment_fiscal_year date NOT NULL, -- 年調年度
 adjustment_type integer NOT NULL DEFAULT 0, -- 年調区分
 personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
 individual_calc_type integer NOT NULL DEFAULT 0, -- 個別計算区分
 employee_code character varying(10) NOT NULL, -- 社員コード
 last_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 姓
 first_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 名
 last_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- カナ姓
 first_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- カナ名
 birth_day date NOT NULL, -- 生年月日
 gender_type integer NOT NULL, -- 性別区分
 section_code character varying(10), -- 所属コード
 position_code character varying(10), -- 職位コード
 office_code character varying(10), -- 事業所コード
 entrance_date date NOT NULL, -- 入社年月日
 retirement_date date, -- 退職年月日
 state_flag integer NOT NULL DEFAULT 1, -- 在職状態区分
 city_code character varying(6), -- 市区町村コード
 compile_code character varying(6), -- とりまとめ先コード
 prefecture character varying(10), -- 都道府県
 address_1 character varying(50), -- 市区町村
 address_2 character varying(50), -- 番地
 address_3 character varying(50), -- 建物情報
 adjustment_calc_type integer NOT NULL DEFAULT 0, -- 年末調整計算区分
 tax_table_type integer NOT NULL DEFAULT 0, -- 税表区分
 minor_type integer NOT NULL DEFAULT 0, -- 未成年区分
 disability_type integer NOT NULL DEFAULT 0, -- 障害者区分
 widow_type integer NOT NULL DEFAULT 0, -- 寡婦区分
 student_type integer NOT NULL DEFAULT 0, -- 学生区分
 death_retirement_type integer NOT NULL DEFAULT 0, -- 死亡退職区分
 accident_type integer NOT NULL DEFAULT 0, -- 災害者区分
 foreigner_type integer NOT NULL DEFAULT 0, -- 外国人区分
 officer_type integer NOT NULL DEFAULT 0, -- 役員区分
 tax_low_204_type integer NOT NULL DEFAULT 0, -- 税法２０４条区分
 mid_entrance_print_type integer NOT NULL DEFAULT 0, -- 中途就職印刷区分
 mid_retirement_print_type integer NOT NULL DEFAULT 0, -- 中途退職印刷区分
 spouse_type integer NOT NULL DEFAULT 0, -- 配偶者区分
 spouse_age_type integer NOT NULL DEFAULT 0, -- 配偶者老人区分
 spouse_special_disability_type integer NOT NULL DEFAULT 0, -- 配偶者障害区分
 dependent integer NOT NULL DEFAULT 0, -- 一般扶養者数
 specific_dependent integer NOT NULL DEFAULT 0, -- 特定扶養者数
 age_dependent integer NOT NULL DEFAULT 0, -- 老人扶養者数
 together_age integer NOT NULL DEFAULT 0, -- 同居老親人数
 disability integer NOT NULL DEFAULT 0, -- 一般障害者数
 special_disablilty integer NOT NULL DEFAULT 0, -- 特別障害者数
 together_special_disability integer NOT NULL DEFAULT 0, -- 同居特別障害者数
 child_dependent integer NOT NULL DEFAULT 0, -- 年少扶養者数
 tax_payroll_total integer NOT NULL DEFAULT 0, -- 課税給与累計
 tax_bonus_total integer NOT NULL DEFAULT 0, -- 課税賞与累計
 adjustment_tax_price integer NOT NULL DEFAULT 0, -- 年調課税調整額
 social_payroll_total integer NOT NULL DEFAULT 0, -- 社保給与累計
 social_bonus_total integer NOT NULL DEFAULT 0, -- 社保賞与累計
 adjustment_social_price integer NOT NULL DEFAULT 0, -- 年調社保調整額
 fixed_contribution_price integer NOT NULL DEFAULT 0, -- 確定拠出掛金
 small_mutual_aid_price integer NOT NULL DEFAULT 0, -- 小規模共済掛金
 income_tax_payroll_total integer NOT NULL DEFAULT 0, -- 所得税給与累計
 income_tax_bonus_total integer NOT NULL DEFAULT 0, -- 所得税賞与累計
 adjustment_income_tax_price integer NOT NULL DEFAULT 0, -- 年調所得税調整額
 last_position_pay_price integer NOT NULL DEFAULT 0, -- 前職支払金額
 last_position_income_price integer NOT NULL DEFAULT 0, -- 前職所得税額
 last_position_social_price integer NOT NULL DEFAULT 0, -- 前職社会保険料
 payroll_outside_income_price integer NOT NULL DEFAULT 0, -- 給与以外所得
 payment_total integer NOT NULL DEFAULT 0, -- 支払金額合計
 income_tax_deduction_price integer NOT NULL DEFAULT 0, -- 所得控除後金額
 social_deduction_price integer NOT NULL DEFAULT 0, -- 社会保険料控除額
 dependent_deduction_price integer NOT NULL DEFAULT 0, -- 扶養控除額
 income_deduction_total integer NOT NULL DEFAULT 0, -- 所得控除合計 
 tax_income integer NOT NULL DEFAULT 0, -- 課税所得
 yearly_tax integer NOT NULL DEFAULT 0, -- 年税額
 balance_year_price integer NOT NULL DEFAULT 0, -- 差引年税額
 adjustment_year_tax_price integer NOT NULL DEFAULT 0, -- 年調年税額
 income_tax_total integer NOT NULL DEFAULT 0, -- 所得税累計
 lack_price integer NOT NULL DEFAULT 0, -- 不足額
 excessive_price integer NOT NULL DEFAULT 0, -- 過納額
 final_business_tax_price integer NOT NULL DEFAULT 0, -- 最終業務充当税額
 check_out_price integer NOT NULL DEFAULT 0, -- 精算額
 levy_price integer NOT NULL DEFAULT 0, -- 徴収額
 refund_price integer NOT NULL DEFAULT 0, -- 還付額
 adjustment_calc_result_type integer NOT NULL DEFAULT 0, -- 年調計算結果区分
 adjustment_payment_type integer NOT NULL DEFAULT 0, -- 年末調整処理区分
 inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
 delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
 insert_date timestamp without time zone NOT NULL, -- 登録日
 insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
 update_date timestamp without time zone NOT NULL, -- 更新日
 update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
 constraint prd_adjustment_calc_pkey primary key (prd_adjustment_calc_id)
)
;
COMMENT ON TABLE prd_adjustment_calc is '年末調整計算データ';
COMMENT ON COLUMN prd_adjustment_calc.prd_adjustment_calc_id is 'レコード識別ID';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_fiscal_year is '年調年度';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_type is '年調区分';
COMMENT ON COLUMN prd_adjustment_calc.personal_id is '個人ID';
COMMENT ON COLUMN prd_adjustment_calc.individual_calc_type is '個別計算区分';
COMMENT ON COLUMN prd_adjustment_calc.employee_code is '社員コード';
COMMENT ON COLUMN prd_adjustment_calc.last_name is '姓';
COMMENT ON COLUMN prd_adjustment_calc.first_name is '名';
COMMENT ON COLUMN prd_adjustment_calc.last_kana is 'カナ姓';
COMMENT ON COLUMN prd_adjustment_calc.first_kana is 'カナ名';
COMMENT ON COLUMN prd_adjustment_calc.birth_day is '生年月日';
COMMENT ON COLUMN prd_adjustment_calc.gender_type is '性別区分';
COMMENT ON COLUMN prd_adjustment_calc.section_code is '所属コード';
COMMENT ON COLUMN prd_adjustment_calc.position_code is '職位コード';
COMMENT ON COLUMN prd_adjustment_calc.office_code is '事業所コード';
COMMENT ON COLUMN prd_adjustment_calc.entrance_date is '入社年月日';
COMMENT ON COLUMN prd_adjustment_calc.retirement_date is '退職年月日';
COMMENT ON COLUMN prd_adjustment_calc.state_flag is '在職状態区分';
COMMENT ON COLUMN prd_adjustment_calc.city_code is '市区町村コード';
COMMENT ON COLUMN prd_adjustment_calc.compile_code is 'とりまとめ先コード';
COMMENT ON COLUMN prd_adjustment_calc.prefecture is '都道府県';
COMMENT ON COLUMN prd_adjustment_calc.address_1 is '市区町村';
COMMENT ON COLUMN prd_adjustment_calc.address_2 is '番地';
COMMENT ON COLUMN prd_adjustment_calc.address_3 is '建物情報';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_calc_type is '年末調整計算区分';
COMMENT ON COLUMN prd_adjustment_calc.tax_table_type is '税表区分';
COMMENT ON COLUMN prd_adjustment_calc.minor_type is '未成年区分';
COMMENT ON COLUMN prd_adjustment_calc.disability_type is '障害者区分';
COMMENT ON COLUMN prd_adjustment_calc.widow_type is '寡婦区分';
COMMENT ON COLUMN prd_adjustment_calc.student_type is '学生区分';
COMMENT ON COLUMN prd_adjustment_calc.death_retirement_type is '死亡退職区分';
COMMENT ON COLUMN prd_adjustment_calc.accident_type is '災害者区分';
COMMENT ON COLUMN prd_adjustment_calc.foreigner_type is '外国人区分';
COMMENT ON COLUMN prd_adjustment_calc.officer_type is '役員区分';
COMMENT ON COLUMN prd_adjustment_calc.tax_low_204_type is '税法２０４条区分';
COMMENT ON COLUMN prd_adjustment_calc.mid_entrance_print_type is '中途就職印刷区分';
COMMENT ON COLUMN prd_adjustment_calc.mid_retirement_print_type is '中途退職印刷区分';
COMMENT ON COLUMN prd_adjustment_calc.spouse_type is '配偶者区分';
COMMENT ON COLUMN prd_adjustment_calc.spouse_age_type is '配偶者老人区分';
COMMENT ON COLUMN prd_adjustment_calc.spouse_special_disability_type is '配偶者障害区分';
COMMENT ON COLUMN prd_adjustment_calc.dependent is '一般扶養者数';
COMMENT ON COLUMN prd_adjustment_calc.specific_dependent is '特定扶養者数';
COMMENT ON COLUMN prd_adjustment_calc.age_dependent is '老人扶養者数';
COMMENT ON COLUMN prd_adjustment_calc.together_age is '同居老親人数';
COMMENT ON COLUMN prd_adjustment_calc.disability is '一般障害者数';
COMMENT ON COLUMN prd_adjustment_calc.special_disablilty is '特別障害者数';
COMMENT ON COLUMN prd_adjustment_calc.together_special_disability is '同居特別障害者数';
COMMENT ON COLUMN prd_adjustment_calc.child_dependent is '年少扶養者数';
COMMENT ON COLUMN prd_adjustment_calc.tax_payroll_total is '課税給与累計';
COMMENT ON COLUMN prd_adjustment_calc.tax_bonus_total is '課税賞与累計';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_tax_price is '年調課税調整額';
COMMENT ON COLUMN prd_adjustment_calc.social_payroll_total is '社保給与累計';
COMMENT ON COLUMN prd_adjustment_calc.social_bonus_total is '社保賞与累計';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_social_price is '年調社保調整額';
COMMENT ON COLUMN prd_adjustment_calc.fixed_contribution_price is '確定拠出掛金';
COMMENT ON COLUMN prd_adjustment_calc.small_mutual_aid_price is '小規模共済掛金';
COMMENT ON COLUMN prd_adjustment_calc.income_tax_payroll_total is '所得税給与累計';
COMMENT ON COLUMN prd_adjustment_calc.income_tax_bonus_total is '所得税賞与累計';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_income_tax_price is '年調所得税調整額';
COMMENT ON COLUMN prd_adjustment_calc.last_position_pay_price is '前職支払金額';
COMMENT ON COLUMN prd_adjustment_calc.last_position_income_price is '前職所得税額';
COMMENT ON COLUMN prd_adjustment_calc.last_position_social_price is '前職社会保険料';
COMMENT ON COLUMN prd_adjustment_calc.payroll_outside_income_price is '給与以外所得';
COMMENT ON COLUMN prd_adjustment_calc.payment_total is '支払金額合計';
COMMENT ON COLUMN prd_adjustment_calc.income_tax_deduction_price is '所得控除後金額';
COMMENT ON COLUMN prd_adjustment_calc.social_deduction_price is '社会保険料控除額';
COMMENT ON COLUMN prd_adjustment_calc.dependent_deduction_price is '扶養控除額';
COMMENT ON COLUMN prd_adjustment_calc.income_deduction_total is '所得控除合計';
COMMENT ON COLUMN prd_adjustment_calc.tax_income is '課税所得';
COMMENT ON COLUMN prd_adjustment_calc.yearly_tax is '年税額';
COMMENT ON COLUMN prd_adjustment_calc.balance_year_price is '差引年税額';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_year_tax_price is '年調年税額';
COMMENT ON COLUMN prd_adjustment_calc.income_tax_total is '所得税累計';
COMMENT ON COLUMN prd_adjustment_calc.lack_price is '不足額';
COMMENT ON COLUMN prd_adjustment_calc.excessive_price is '過納額';
COMMENT ON COLUMN prd_adjustment_calc.final_business_tax_price is '最終業務充当税額';
COMMENT ON COLUMN prd_adjustment_calc.check_out_price is '精算額';
COMMENT ON COLUMN prd_adjustment_calc.levy_price is '徴収額';
COMMENT ON COLUMN prd_adjustment_calc.refund_price is '還付額';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_calc_result_type is '年調計算結果区分';
COMMENT ON COLUMN prd_adjustment_calc.adjustment_payment_type is '年末調整処理区分';
COMMENT ON COLUMN prd_adjustment_calc.inactivate_flag is '無効フラグ';
COMMENT ON COLUMN prd_adjustment_calc.delete_flag is '削除フラグ';
COMMENT ON COLUMN prd_adjustment_calc.insert_date is '登録日';
COMMENT ON COLUMN prd_adjustment_calc.insert_user is '登録者';
COMMENT ON COLUMN prd_adjustment_calc.update_date is '更新日';
COMMENT ON COLUMN prd_adjustment_calc.update_user is '更新者';

CREATE TABLE prd_adjustment_dependent_detail
(
 prd_adjustment_dependent_detail_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
 adjustment_fiscal_year date NOT NULL, -- 年調年度
 personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
 dependent_seq integer NOT NULL DEFAULT 0, -- 扶養者連番
 family_relationship_type integer NOT NULL DEFAULT 0, -- 続柄
 family_relationship_other character varying(10) DEFAULT ''::character varying, -- 続柄（詳細）
 last_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 姓
 first_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 名
 last_kana character varying(50), -- カナ姓
 first_kana character varying(50), -- カナ名
 birth_date date NOT NULL, -- 生年月日
 gender_type integer NOT NULL DEFAULT 1, -- 性別区分
 together_type integer NOT NULL DEFAULT 1, -- 同居区分
 disability_type integer NOT NULL DEFAULT 0, -- 障害区分
 income_promising_frame integer NOT NULL DEFAULT 0, -- 所得見込額
 dependent_start_date date, -- 扶養開始日
 dependent_end_date date, -- 扶養終了日
 resident_type integer NOT NULL DEFAULT 0, -- 居住者区分
 delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
 insert_date timestamp without time zone NOT NULL, -- 登録日
 insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
 update_date timestamp without time zone NOT NULL, -- 更新日
 update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
 constraint prd_adjustment_dependent_detail_pkey primary key (prd_adjustment_dependent_detail_id)
)
;

COMMENT ON TABLE prd_adjustment_dependent_detail IS '年末調整扶養者情報';
COMMENT ON COLUMN prd_adjustment_dependent_detail.prd_adjustment_dependent_detail_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_adjustment_dependent_detail.adjustment_fiscal_year IS '年調年度';
COMMENT ON COLUMN prd_adjustment_dependent_detail.personal_id IS '個人ID';
COMMENT ON COLUMN prd_adjustment_dependent_detail.dependent_seq IS '扶養者連番';
COMMENT ON COLUMN prd_adjustment_dependent_detail.family_relationship_type IS '続柄';
COMMENT ON COLUMN prd_adjustment_dependent_detail.family_relationship_other IS '続柄（詳細）';
COMMENT ON COLUMN prd_adjustment_dependent_detail.last_name IS '姓';
COMMENT ON COLUMN prd_adjustment_dependent_detail.first_name IS '名';
COMMENT ON COLUMN prd_adjustment_dependent_detail.last_kana IS 'カナ姓';
COMMENT ON COLUMN prd_adjustment_dependent_detail.first_kana IS 'カナ名';
COMMENT ON COLUMN prd_adjustment_dependent_detail.birth_date IS '生年月日';
COMMENT ON COLUMN prd_adjustment_dependent_detail.gender_type IS '性別区分';
COMMENT ON COLUMN prd_adjustment_dependent_detail.together_type IS '同居区分';
COMMENT ON COLUMN prd_adjustment_dependent_detail.disability_type IS '障害区分';
COMMENT ON COLUMN prd_adjustment_dependent_detail.income_promising_frame IS '所得見込額';
COMMENT ON COLUMN prd_adjustment_dependent_detail.dependent_start_date IS '扶養開始日';
COMMENT ON COLUMN prd_adjustment_dependent_detail.dependent_end_date IS '扶養終了日';
COMMENT ON COLUMN prd_adjustment_dependent_detail.resident_type IS '居住者区分';
COMMENT ON COLUMN prd_adjustment_dependent_detail.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_adjustment_dependent_detail.insert_date IS '登録日';
COMMENT ON COLUMN prd_adjustment_dependent_detail.insert_user IS '登録者';
COMMENT ON COLUMN prd_adjustment_dependent_detail.update_date IS '更新日';
COMMENT ON COLUMN prd_adjustment_dependent_detail.update_user IS '更新者';


CREATE TABLE prm_adjustment_defined
(
 prm_adjustment_defined_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
 adjustment_fiscal_year date NOT NULL, -- 年調年度
 section_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 所属コード
 adjustment_defined_status integer NOT NULL DEFAULT 0, -- 確定状態
 delete_flag integer NOT NULL default 0, -- 削除フラグ
 insert_date timestamp without time zone NOT NULL, -- 登録日
 insert_user character varying(50) NOT NULL, -- 登録者
 update_date timestamp without time zone NOT NULL, -- 更新日
 update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
 constraint prm_adjustment_defined_pkey primary key (prm_adjustment_defined_id)
)
;

COMMENT ON TABLE prm_adjustment_defined is '年調確定制御マスタ';
COMMENT ON COLUMN prm_adjustment_defined.prm_adjustment_defined_id is 'レコード識別ID';
COMMENT ON COLUMN prm_adjustment_defined.adjustment_fiscal_year is '年調年度';
COMMENT ON COLUMN prm_adjustment_defined.section_code is '所属コード';
COMMENT ON COLUMN prm_adjustment_defined.adjustment_defined_status is '確定状態';
COMMENT ON COLUMN prm_adjustment_defined.delete_flag is '削除フラグ';
COMMENT ON COLUMN prm_adjustment_defined.insert_date is '登録日';
COMMENT ON COLUMN prm_adjustment_defined.insert_user is '登録者';
COMMENT ON COLUMN prm_adjustment_defined.update_date is '更新日';
COMMENT ON COLUMN prm_adjustment_defined.update_user is '更新者';


CREATE TABLE prd_adjustment_report
(
  prd_adjustment_report_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  adjustment_year date NOT NULL, -- 年調年度
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  employee_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 社員コード
  withholding_address_type integer NOT NULL DEFAULT 0, -- 源泉住所入力区分
  city_code character varying(6), -- 市区町村コード
  prefecture character varying(10), -- 都道府県
  address_1 character varying(50), -- 市区町村
  address_2 character varying(50), -- 番地
  address_3 character varying(50), -- 建物情報
  death_retirement_type integer NOT NULL DEFAULT 0, -- 死亡退職区分
  accident_type integer NOT NULL DEFAULT 0, -- 災害者区分
  foreigner_type integer NOT NULL DEFAULT 0, -- 外国人区分
  officer_type integer NOT NULL DEFAULT 0, -- 役員区分
  tax_low_204_type integer NOT NULL DEFAULT 0, -- 税法２０４条区分
  mid_entrance_print_type integer NOT NULL DEFAULT 0, -- 中途就職印刷区分
  mid_retirement_print_type integer NOT NULL DEFAULT 0, -- 中途退職印刷区分
  tax_adjustment integer NOT NULL DEFAULT 0, -- 課税調整額
  social_adjustment integer NOT NULL DEFAULT 0, -- 社保調整額
  fixed_contribution integer NOT NULL DEFAULT 0, -- 確定拠出掛金額
  small_mutual_aid integer NOT NULL DEFAULT 0, -- 小規模共済掛金額
  income_tax_adjustment integer NOT NULL DEFAULT 0, -- 所得税調整額
  last_position_pay integer NOT NULL DEFAULT 0, -- 前職支払金額
  last_position_income integer NOT NULL DEFAULT 0, -- 前職所得税額
  last_position_social integer NOT NULL DEFAULT 0, -- 前職社会保険料
  payroll_outside_income integer NOT NULL DEFAULT 0, -- 給与以外所得
  old_life_insurance integer NOT NULL DEFAULT 0, -- 生命保険料(旧)
  new_life_insurance integer NOT NULL DEFAULT 0, -- 生命保険料(新)
  old_personal_pension integer NOT NULL DEFAULT 0, -- 個人年金保険料(旧)
  new_personal_pension integer NOT NULL DEFAULT 0, -- 個人年金保険料(新)
  care_insurance integer NOT NULL DEFAULT 0, -- 介護保険料
  life_insurance_deduction integer NOT NULL DEFAULT 0, -- 生命保険料控除額
  earthquake integer NOT NULL DEFAULT 0, -- 地震保険料
  long_damage integer NOT NULL DEFAULT 0, -- 旧長期損害保険料
  damage_deduction integer NOT NULL DEFAULT 0, -- 地震保険料控除額
  personal_national integer NOT NULL DEFAULT 0, -- 個人国年保険料等
  personal_social_1 integer NOT NULL DEFAULT 0, -- 個人社会保険料1
  personal_social_2 integer NOT NULL DEFAULT 0, -- 個人社会保険料2
  social_deduction integer NOT NULL DEFAULT 0, -- 申告社会保険料控除額
  small_company_mutual_aid integer NOT NULL DEFAULT 0, -- 中小企業共済掛金
  personal_pension_premium integer NOT NULL DEFAULT 0, -- 個人年金掛金
  disability_mutual_aid integer NOT NULL DEFAULT 0, -- 心身障害者共済掛金
  small_mutual_aid_deduction integer NOT NULL DEFAULT 0, -- 小規模共済掛金控除額
  spouse_total_income integer NOT NULL DEFAULT 0, -- 配偶者合計所得
  spouse_special_deduction integer NOT NULL DEFAULT 0, -- 配偶者特別控除額
  acquire_house_deduction integer NOT NULL DEFAULT 0, -- 住宅取得特別控除額
  house_deduction_number integer NOT NULL DEFAULT 0, -- 住宅借入金等特別控除適用数
  live_start_date_1 date, -- 居住開始年月日(1回目)
  live_start_date_2 date, -- 居住開始年月日(2回目)
  house_deduction_type_1 integer NOT NULL DEFAULT 0, -- 住宅借入金等特別控除区分(1回目)
  house_deduction_type_2 integer NOT NULL DEFAULT 0, -- 住宅借入金等特別控除区分(2回目)
  house_deduction_remainder_1 integer NOT NULL DEFAULT 0, -- 住宅借入金等年末残高(1回目)
  house_deduction_remainder_2 integer NOT NULL DEFAULT 0, -- 住宅借入金等年末残高(2回目)
  summary character varying(180), -- 摘要欄
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_adjustment_report_pkey PRIMARY KEY (prd_adjustment_report_id )
)
;
COMMENT ON TABLE prd_adjustment_report IS '年末調整申告データ';
COMMENT ON COLUMN prd_adjustment_report.prd_adjustment_report_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_adjustment_report.adjustment_year IS '年調年度';
COMMENT ON COLUMN prd_adjustment_report.personal_id IS '個人ID';
COMMENT ON COLUMN prd_adjustment_report.employee_code IS '社員コード';
COMMENT ON COLUMN prd_adjustment_report.withholding_address_type IS '源泉住所入力区分';
COMMENT ON COLUMN prd_adjustment_report.city_code IS '市区町村コード';
COMMENT ON COLUMN prd_adjustment_report.prefecture IS '都道府県';
COMMENT ON COLUMN prd_adjustment_report.address_1 IS '市区町村';
COMMENT ON COLUMN prd_adjustment_report.address_2 IS '番地';
COMMENT ON COLUMN prd_adjustment_report.address_3 IS '建物情報';
COMMENT ON COLUMN prd_adjustment_report.death_retirement_type IS '死亡退職区分';
COMMENT ON COLUMN prd_adjustment_report.accident_type IS '災害者区分';
COMMENT ON COLUMN prd_adjustment_report.foreigner_type IS '外国人区分';
COMMENT ON COLUMN prd_adjustment_report.officer_type IS '役員区分';
COMMENT ON COLUMN prd_adjustment_report.tax_low_204_type IS '税法２０４条区分';
COMMENT ON COLUMN prd_adjustment_report.mid_entrance_print_type IS '中途就職印刷区分';
COMMENT ON COLUMN prd_adjustment_report.mid_retirement_print_type IS '中途退職印刷区分';
COMMENT ON COLUMN prd_adjustment_report.tax_adjustment IS '課税調整額';
COMMENT ON COLUMN prd_adjustment_report.social_adjustment IS '社保調整額';
COMMENT ON COLUMN prd_adjustment_report.fixed_contribution IS '確定拠出掛金額';
COMMENT ON COLUMN prd_adjustment_report.small_mutual_aid IS '小規模共済掛金額';
COMMENT ON COLUMN prd_adjustment_report.income_tax_adjustment IS '所得税調整額';
COMMENT ON COLUMN prd_adjustment_report.last_position_pay IS '前職支払金額';
COMMENT ON COLUMN prd_adjustment_report.last_position_income IS '前職所得税額';
COMMENT ON COLUMN prd_adjustment_report.last_position_social IS '前職社会保険料';
COMMENT ON COLUMN prd_adjustment_report.payroll_outside_income IS '給与以外所得';
COMMENT ON COLUMN prd_adjustment_report.old_life_insurance IS '生命保険料(旧)';
COMMENT ON COLUMN prd_adjustment_report.new_life_insurance IS '生命保険料(新)';
COMMENT ON COLUMN prd_adjustment_report.old_personal_pension IS '個人年金保険料(旧)';
COMMENT ON COLUMN prd_adjustment_report.new_personal_pension IS '個人年金保険料(新)';
COMMENT ON COLUMN prd_adjustment_report.care_insurance IS '介護保険料';
COMMENT ON COLUMN prd_adjustment_report.life_insurance_deduction IS '生命保険料控除額';
COMMENT ON COLUMN prd_adjustment_report.earthquake IS '地震保険料';
COMMENT ON COLUMN prd_adjustment_report.long_damage IS '旧長期損害保険料';
COMMENT ON COLUMN prd_adjustment_report.damage_deduction IS '地震保険料控除額';
COMMENT ON COLUMN prd_adjustment_report.personal_national IS '個人国年保険料等';
COMMENT ON COLUMN prd_adjustment_report.personal_social_1 IS '個人社会保険料1';
COMMENT ON COLUMN prd_adjustment_report.personal_social_2 IS '個人社会保険料2';
COMMENT ON COLUMN prd_adjustment_report.social_deduction IS '申告社会保険料控除額';
COMMENT ON COLUMN prd_adjustment_report.small_company_mutual_aid IS '中小企業共済掛金';
COMMENT ON COLUMN prd_adjustment_report.personal_pension_premium IS '個人年金掛金';
COMMENT ON COLUMN prd_adjustment_report.disability_mutual_aid IS '心身障害者共済掛金';
COMMENT ON COLUMN prd_adjustment_report.small_mutual_aid_deduction IS '小規模共済掛金控除額';
COMMENT ON COLUMN prd_adjustment_report.spouse_total_income IS '配偶者合計所得';
COMMENT ON COLUMN prd_adjustment_report.spouse_special_deduction IS '配偶者特別控除額';
COMMENT ON COLUMN prd_adjustment_report.acquire_house_deduction IS '住宅取得特別控除額';
COMMENT ON COLUMN prd_adjustment_report.house_deduction_number IS '住宅借入金等特別控除適用数';
COMMENT ON COLUMN prd_adjustment_report.live_start_date_1 IS '居住開始年月日(1回目)';
COMMENT ON COLUMN prd_adjustment_report.live_start_date_2 IS '居住開始年月日(2回目)';
COMMENT ON COLUMN prd_adjustment_report.house_deduction_type_1 IS '住宅借入金等特別控除区分(1回目)';
COMMENT ON COLUMN prd_adjustment_report.house_deduction_type_2 IS '住宅借入金等特別控除区分(2回目)';
COMMENT ON COLUMN prd_adjustment_report.house_deduction_remainder_1 IS '住宅借入金等年末残高(1回目)';
COMMENT ON COLUMN prd_adjustment_report.house_deduction_remainder_2 IS '住宅借入金等年末残高(2回目)';
COMMENT ON COLUMN prd_adjustment_report.summary IS '摘要欄';
COMMENT ON COLUMN prd_adjustment_report.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_adjustment_report.insert_date IS '登録日';
COMMENT ON COLUMN prd_adjustment_report.insert_user IS '登録者';
COMMENT ON COLUMN prd_adjustment_report.update_date IS '更新日';
COMMENT ON COLUMN prd_adjustment_report.update_user IS '更新者';


CREATE TABLE prd_bonus_parameter_calc
(
  prd_bonus_parameter_calc_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 処理年月
  execute_times integer NOT NULL DEFAULT 1, -- 処理回数
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  bonus_work_item_1 double precision NOT NULL DEFAULT 0, -- 賞与勤怠項目1
  bonus_work_item_2 double precision NOT NULL DEFAULT 0, -- 賞与勤怠項目2
  bonus_work_item_3 double precision NOT NULL DEFAULT 0, -- 賞与勤怠項目3
  bonus_work_item_4 double precision NOT NULL DEFAULT 0, -- 賞与勤怠項目4
  bonus_work_item_5 double precision NOT NULL DEFAULT 0, -- 賞与勤怠項目5
  rate_item_1 double precision NOT NULL DEFAULT 0, -- 倍率項目1
  rate_item_2 double precision NOT NULL DEFAULT 0, -- 倍率項目2
  rate_item_3 double precision NOT NULL DEFAULT 0, -- 倍率項目3
  rate_item_4 double precision NOT NULL DEFAULT 0, -- 倍率項目4
  rate_item_5 double precision NOT NULL DEFAULT 0, -- 倍率項目5
  monthly_bonus_price double precision NOT NULL DEFAULT 0, -- 完全月給賞与基準額
  diem_monthly_bonus_price double precision NOT NULL DEFAULT 0, -- 日給月給賞与基準額
  diem_bonus_price double precision NOT NULL DEFAULT 0, -- 日給賞与基準額
  hourly_bonus_price double precision NOT NULL DEFAULT 0, -- 時給賞与基準額
  unit_item_1 double precision NOT NULL DEFAULT 0, -- 単価項目1
  unit_item_2 double precision NOT NULL DEFAULT 0, -- 単価項目2
  unit_item_3 double precision NOT NULL DEFAULT 0, -- 単価項目3
  unit_item_4 double precision NOT NULL DEFAULT 0, -- 単価項目4
  unit_item_5 double precision NOT NULL DEFAULT 0, -- 単価項目5
  bonus_price_1 integer NOT NULL DEFAULT 0, -- 賞与額1
  bonus_price_2 integer NOT NULL DEFAULT 0, -- 賞与額2
  bonus_price_3 integer NOT NULL DEFAULT 0, -- 賞与額3
  bonus_price_4 integer NOT NULL DEFAULT 0, -- 賞与額4
  bonus_price_5 integer NOT NULL DEFAULT 0, -- 賞与額5
  bonus_price_6 integer NOT NULL DEFAULT 0, -- 賞与額6
  bonus_price_7 integer NOT NULL DEFAULT 0, -- 賞与額7
  bonus_price_8 integer NOT NULL DEFAULT 0, -- 賞与額8
  bonus_price_9 integer NOT NULL DEFAULT 0, -- 賞与額9
  bonus_price_10 integer NOT NULL DEFAULT 0, -- 賞与額10
  calc_pay_item_1 integer NOT NULL DEFAULT 0, -- 計算支給項目1
  calc_pay_item_2 integer NOT NULL DEFAULT 0, -- 計算支給項目2
  calc_pay_item_3 integer NOT NULL DEFAULT 0, -- 計算支給項目3
  calc_pay_item_4 integer NOT NULL DEFAULT 0, -- 計算支給項目4
  calc_pay_item_5 integer NOT NULL DEFAULT 0, -- 計算支給項目5
  calc_pay_item_6 integer NOT NULL DEFAULT 0, -- 計算支給項目6
  calc_pay_item_7 integer NOT NULL DEFAULT 0, -- 計算支給項目7
  calc_pay_item_8 integer NOT NULL DEFAULT 0, -- 計算支給項目8
  calc_pay_item_9 integer NOT NULL DEFAULT 0, -- 計算支給項目9
  calc_pay_item_10 integer NOT NULL DEFAULT 0, -- 計算支給項目10
  total_pay_price integer NOT NULL DEFAULT 0, -- 総支給額
  no_tax_price integer NOT NULL DEFAULT 0, -- 非課税対象額
  reward_fixed_price integer NOT NULL DEFAULT 0, -- 報酬固定対象額
  employment_price integer NOT NULL DEFAULT 0, -- 雇用保険対象額
  payroll_price integer NOT NULL DEFAULT 0, -- 賃金対象額
  other_pay_price integer NOT NULL DEFAULT 0, -- その他支給額
  employment_insurance integer NOT NULL DEFAULT 0, -- 雇用保険料
  adjustment_employment_insurance integer NOT NULL DEFAULT 0, -- 調整雇用保険料
  already_health_reward integer NOT NULL DEFAULT 0, -- 既払健保報酬対象額
  already_health_basic integer NOT NULL DEFAULT 0, -- 既払健保標準賞与額
  already_health_insurance integer NOT NULL DEFAULT 0, -- 既払健康保険料
  already_care_insurance integer NOT NULL DEFAULT 0, -- 既払介護保険料
  health_basic_bonus integer NOT NULL DEFAULT 0, -- 健保標準賞与額
  already_employee_reward integer NOT NULL DEFAULT 0, -- 既払厚年報酬対象額
  already_employee_basic integer NOT NULL DEFAULT 0, -- 既払厚年標準賞与額
  already_employee_pension integer NOT NULL DEFAULT 0, -- 既払厚生年金
  already_fund_price integer NOT NULL DEFAULT 0, -- 既払厚生年金基金
  employee_basic_bonus integer NOT NULL DEFAULT 0, -- 厚年標準賞与額
  health_insurance integer NOT NULL DEFAULT 0, -- 健康保険料
  care_insurance integer NOT NULL DEFAULT 0, -- 介護保険料
  employee_price integer NOT NULL DEFAULT 0, -- 厚生年金
  fund_price integer NOT NULL DEFAULT 0, -- 厚生年金基金
  health_basic_insurance integer NOT NULL DEFAULT 0, -- 健保基本保険料
  health_specific_insurance integer NOT NULL DEFAULT 0, -- 健保特定保険料
  adjustment_health_insurance integer NOT NULL DEFAULT 0, -- 調整健康保険料
  adjustment_care_insurance integer NOT NULL DEFAULT 0, -- 調整介護保険料
  adjustment_employee_price integer NOT NULL DEFAULT 0, -- 調整厚生年金
  adjustment_fund_price integer NOT NULL DEFAULT 0, -- 調整厚生年金基金
  tax_price integer NOT NULL DEFAULT 0, -- 課税対象額
  income_tax_exception integer NOT NULL DEFAULT 0, -- 所得税例外区分
  income_tax_rate double precision NOT NULL DEFAULT 0, -- 所得税計算税率
  bonus_deduction_item_1 integer NOT NULL DEFAULT 0, -- 賞与控除項目1
  bonus_deduction_item_2 integer NOT NULL DEFAULT 0, -- 賞与控除項目2
  bonus_deduction_item_3 integer NOT NULL DEFAULT 0, -- 賞与控除項目3
  bonus_deduction_item_4 integer NOT NULL DEFAULT 0, -- 賞与控除項目4
  bonus_deduction_item_5 integer NOT NULL DEFAULT 0, -- 賞与控除項目5
  bonus_deduction_item_6 integer NOT NULL DEFAULT 0, -- 賞与控除項目6
  bonus_deduction_item_7 integer NOT NULL DEFAULT 0, -- 賞与控除項目7
  bonus_deduction_item_8 integer NOT NULL DEFAULT 0, -- 賞与控除項目8
  bonus_deduction_item_9 integer NOT NULL DEFAULT 0, -- 賞与控除項目9
  bonus_deduction_item_10 integer NOT NULL DEFAULT 0, -- 賞与控除項目10
  bonus_deduction_item_11 integer NOT NULL DEFAULT 0, -- 賞与控除項目11
  bonus_deduction_item_12 integer NOT NULL DEFAULT 0, -- 賞与控除項目12
  bonus_deduction_item_13 integer NOT NULL DEFAULT 0, -- 賞与控除項目13
  bonus_deduction_item_14 integer NOT NULL DEFAULT 0, -- 賞与控除項目14
  bonus_deduction_item_15 integer NOT NULL DEFAULT 0, -- 賞与控除項目15
  bonus_deduction_item_16 integer NOT NULL DEFAULT 0, -- 賞与控除項目16
  bonus_deduction_item_17 integer NOT NULL DEFAULT 0, -- 賞与控除項目17
  bonus_deduction_item_18 integer NOT NULL DEFAULT 0, -- 賞与控除項目18
  bonus_deduction_item_19 integer NOT NULL DEFAULT 0, -- 賞与控除項目19
  bonus_deduction_item_20 integer NOT NULL DEFAULT 0, -- 賞与控除項目20
  bonus_deduction_item_21 integer NOT NULL DEFAULT 0, -- 賞与控除項目21
  bonus_deduction_item_22 integer NOT NULL DEFAULT 0, -- 賞与控除項目22
  bonus_deduction_item_23 integer NOT NULL DEFAULT 0, -- 賞与控除項目23
  bonus_deduction_item_24 integer NOT NULL DEFAULT 0, -- 賞与控除項目24
  bonus_deduction_item_25 integer NOT NULL DEFAULT 0, -- 賞与控除項目25
  bonus_deduction_item_26 integer NOT NULL DEFAULT 0, -- 賞与控除項目26
  bonus_deduction_item_27 integer NOT NULL DEFAULT 0, -- 賞与控除項目27
  bonus_deduction_item_28 integer NOT NULL DEFAULT 0, -- 賞与控除項目28
  bonus_deduction_item_29 integer NOT NULL DEFAULT 0, -- 賞与控除項目29
  bonus_deduction_item_30 integer NOT NULL DEFAULT 0, -- 賞与控除項目30
  calc_deduction_item_1 integer NOT NULL DEFAULT 0, -- 計算控除項目1
  calc_deduction_item_2 integer NOT NULL DEFAULT 0, -- 計算控除項目2
  calc_deduction_item_3 integer NOT NULL DEFAULT 0, -- 計算控除項目3
  calc_deduction_item_4 integer NOT NULL DEFAULT 0, -- 計算控除項目4
  calc_deduction_item_5 integer NOT NULL DEFAULT 0, -- 計算控除項目5
  calc_deduction_item_6 integer NOT NULL DEFAULT 0, -- 計算控除項目6
  calc_deduction_item_7 integer NOT NULL DEFAULT 0, -- 計算控除項目7
  calc_deduction_item_8 integer NOT NULL DEFAULT 0, -- 計算控除項目8
  calc_deduction_item_9 integer NOT NULL DEFAULT 0, -- 計算控除項目9
  calc_deduction_item_10 integer NOT NULL DEFAULT 0, -- 計算控除項目10
  change_deduction_item_1 integer NOT NULL DEFAULT 0, -- 変動控除項目1
  change_deduction_item_2 integer NOT NULL DEFAULT 0, -- 変動控除項目2
  change_deduction_item_3 integer NOT NULL DEFAULT 0, -- 変動控除項目3
  change_deduction_item_4 integer NOT NULL DEFAULT 0, -- 変動控除項目4
  change_deduction_item_5 integer NOT NULL DEFAULT 0, -- 変動控除項目5
  change_deduction_item_6 integer NOT NULL DEFAULT 0, -- 変動控除項目6
  change_deduction_item_7 integer NOT NULL DEFAULT 0, -- 変動控除項目7
  change_deduction_item_8 integer NOT NULL DEFAULT 0, -- 変動控除項目8
  change_deduction_item_9 integer NOT NULL DEFAULT 0, -- 変動控除項目9
  change_deduction_item_10 integer NOT NULL DEFAULT 0, -- 変動控除項目10
  change_deduction_item_11 integer NOT NULL DEFAULT 0, -- 変動控除項目11
  change_deduction_item_12 integer NOT NULL DEFAULT 0, -- 変動控除項目12
  change_deduction_item_13 integer NOT NULL DEFAULT 0, -- 変動控除項目13
  change_deduction_item_14 integer NOT NULL DEFAULT 0, -- 変動控除項目14
  change_deduction_item_15 integer NOT NULL DEFAULT 0, -- 変動控除項目15
  change_deduction_item_16 integer NOT NULL DEFAULT 0, -- 変動控除項目16
  change_deduction_item_17 integer NOT NULL DEFAULT 0, -- 変動控除項目17
  change_deduction_item_18 integer NOT NULL DEFAULT 0, -- 変動控除項目18
  change_deduction_item_19 integer NOT NULL DEFAULT 0, -- 変動控除項目19
  change_deduction_item_20 integer NOT NULL DEFAULT 0, -- 変動控除項目20
  adjustment_excessive integer NOT NULL DEFAULT 0, -- 年調過不足額
  deduction_total integer NOT NULL DEFAULT 0, -- 控除合計
  balance_pay_price integer NOT NULL DEFAULT 0, -- 差引支給額
  account_price_1 integer NOT NULL DEFAULT 0, -- 口座１振込額
  account_price_2 integer NOT NULL DEFAULT 0, -- 口座２振込額
  account_price_3 integer NOT NULL DEFAULT 0, -- 口座３振込額
  cash_pay_price integer NOT NULL DEFAULT 0, -- 現金支給額
  yearly_total_pay_price integer NOT NULL DEFAULT 0, -- 年間総支給額
  yearly_withholding_price integer NOT NULL DEFAULT 0, -- 年間源泉対象額
  yearly_no_tax_price integer NOT NULL DEFAULT 0, -- 年間非課税対象額
  yearly_social_price integer NOT NULL DEFAULT 0, -- 年間社会保険料
  yearly_income_tax_price integer NOT NULL DEFAULT 0, -- 年間所得税
  owner_employment_insurance integer NOT NULL DEFAULT 0, -- 事業主雇用保険料
  owner_accident_insurance integer NOT NULL DEFAULT 0, -- 事業主労災保険料
  owner_adjustment_employment_insurance integer NOT NULL DEFAULT 0, -- 事業主調整雇用保険料
  owner_adjustment_accident_insurance integer NOT NULL DEFAULT 0, -- 事業主調整労災保険料
  owner_health_insurance integer NOT NULL DEFAULT 0, -- 事業主健康保険料
  owner_care_insurance integer NOT NULL DEFAULT 0, -- 事業主介護保険料
  owner_employee_price integer NOT NULL DEFAULT 0, -- 事業主厚生年金
  owner_fund_price integer NOT NULL DEFAULT 0, -- 事業主厚生年金基金
  owner_child_price integer NOT NULL DEFAULT 0, -- 事業主児童手当拠出金
  owner_health_basic_insurance integer NOT NULL DEFAULT 0, -- 事業主健保基本保険料
  owner_health_specific_insurance integer NOT NULL DEFAULT 0, -- 事業主健保特定保険料
  owner_adjustment_health_insurance integer NOT NULL DEFAULT 0, -- 事業主調整健康保険料
  owner_adjustment_care_insurance integer NOT NULL DEFAULT 0, -- 事業主調整介護保険料
  owner_adjustment_employee_price integer NOT NULL DEFAULT 0, -- 事業主調整厚生年金
  owner_adjustment_fund_price integer NOT NULL DEFAULT 0, -- 事業主調整厚生年金基金
  owner_adjustment_child_price integer NOT NULL DEFAULT 0, -- 事業主調整児童手当拠出金
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_bonus_parameter_calc_pkey PRIMARY KEY (prd_bonus_parameter_calc_id )
)
;
COMMENT ON TABLE prd_bonus_parameter_calc IS '賞与計算パラメータデータ';
COMMENT ON COLUMN prd_bonus_parameter_calc.prd_bonus_parameter_calc_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_bonus_parameter_calc.execute_date IS '処理年月';
COMMENT ON COLUMN prd_bonus_parameter_calc.execute_times IS '処理回数';
COMMENT ON COLUMN prd_bonus_parameter_calc.personal_id IS '個人ID';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_work_item_1 IS '賞与勤怠項目1';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_work_item_2 IS '賞与勤怠項目2';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_work_item_3 IS '賞与勤怠項目3';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_work_item_4 IS '賞与勤怠項目4';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_work_item_5 IS '賞与勤怠項目5';
COMMENT ON COLUMN prd_bonus_parameter_calc.rate_item_1 IS '倍率項目1';
COMMENT ON COLUMN prd_bonus_parameter_calc.rate_item_2 IS '倍率項目2';
COMMENT ON COLUMN prd_bonus_parameter_calc.rate_item_3 IS '倍率項目3';
COMMENT ON COLUMN prd_bonus_parameter_calc.rate_item_4 IS '倍率項目4';
COMMENT ON COLUMN prd_bonus_parameter_calc.rate_item_5 IS '倍率項目5';
COMMENT ON COLUMN prd_bonus_parameter_calc.monthly_bonus_price IS '完全月給賞与基準額';
COMMENT ON COLUMN prd_bonus_parameter_calc.diem_monthly_bonus_price IS '日給月給賞与基準額';
COMMENT ON COLUMN prd_bonus_parameter_calc.diem_bonus_price IS '日給賞与基準額';
COMMENT ON COLUMN prd_bonus_parameter_calc.hourly_bonus_price IS '時給賞与基準額';
COMMENT ON COLUMN prd_bonus_parameter_calc.unit_item_1 IS '単価項目1';
COMMENT ON COLUMN prd_bonus_parameter_calc.unit_item_2 IS '単価項目2';
COMMENT ON COLUMN prd_bonus_parameter_calc.unit_item_3 IS '単価項目3';
COMMENT ON COLUMN prd_bonus_parameter_calc.unit_item_4 IS '単価項目4';
COMMENT ON COLUMN prd_bonus_parameter_calc.unit_item_5 IS '単価項目5';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_1 IS '賞与額1';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_2 IS '賞与額2';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_3 IS '賞与額3';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_4 IS '賞与額4';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_5 IS '賞与額5';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_6 IS '賞与額6';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_7 IS '賞与額7';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_8 IS '賞与額8';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_9 IS '賞与額9';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_price_10 IS '賞与額10';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_1 IS '計算支給項目1';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_2 IS '計算支給項目2';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_3 IS '計算支給項目3';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_4 IS '計算支給項目4';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_5 IS '計算支給項目5';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_6 IS '計算支給項目6';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_7 IS '計算支給項目7';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_8 IS '計算支給項目8';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_9 IS '計算支給項目9';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_pay_item_10 IS '計算支給項目10';
COMMENT ON COLUMN prd_bonus_parameter_calc.total_pay_price IS '総支給額';
COMMENT ON COLUMN prd_bonus_parameter_calc.no_tax_price IS '非課税対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.reward_fixed_price IS '報酬固定対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.employment_price IS '雇用保険対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.payroll_price IS '賃金対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.other_pay_price IS 'その他支給額';
COMMENT ON COLUMN prd_bonus_parameter_calc.employment_insurance IS '雇用保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.adjustment_employment_insurance IS '調整雇用保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_health_reward IS '既払健保報酬対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_health_basic IS '既払健保標準賞与額';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_health_insurance IS '既払健康保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_care_insurance IS '既払介護保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.health_basic_bonus IS '健保標準賞与額';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_employee_reward IS '既払厚年報酬対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_employee_basic IS '既払厚年標準賞与額';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_employee_pension IS '既払厚生年金';
COMMENT ON COLUMN prd_bonus_parameter_calc.already_fund_price IS '既払厚生年金基金';
COMMENT ON COLUMN prd_bonus_parameter_calc.employee_basic_bonus IS '厚年標準賞与額';
COMMENT ON COLUMN prd_bonus_parameter_calc.health_insurance IS '健康保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.care_insurance IS '介護保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.employee_price IS '厚生年金';
COMMENT ON COLUMN prd_bonus_parameter_calc.fund_price IS '厚生年金基金';
COMMENT ON COLUMN prd_bonus_parameter_calc.health_basic_insurance IS '健保基本保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.health_specific_insurance IS '健保特定保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.adjustment_health_insurance IS '調整健康保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.adjustment_care_insurance IS '調整介護保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.adjustment_employee_price IS '調整厚生年金';
COMMENT ON COLUMN prd_bonus_parameter_calc.adjustment_fund_price IS '調整厚生年金基金';
COMMENT ON COLUMN prd_bonus_parameter_calc.tax_price IS '課税対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.income_tax_exception IS '所得税例外区分';
COMMENT ON COLUMN prd_bonus_parameter_calc.income_tax_rate IS '所得税計算税率';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_1 IS '賞与控除項目1';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_2 IS '賞与控除項目2';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_3 IS '賞与控除項目3';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_4 IS '賞与控除項目4';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_5 IS '賞与控除項目5';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_6 IS '賞与控除項目6';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_7 IS '賞与控除項目7';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_8 IS '賞与控除項目8';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_9 IS '賞与控除項目9';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_10 IS '賞与控除項目10';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_11 IS '賞与控除項目11';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_12 IS '賞与控除項目12';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_13 IS '賞与控除項目13';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_14 IS '賞与控除項目14';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_15 IS '賞与控除項目15';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_16 IS '賞与控除項目16';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_17 IS '賞与控除項目17';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_18 IS '賞与控除項目18';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_19 IS '賞与控除項目19';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_20 IS '賞与控除項目20';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_21 IS '賞与控除項目21';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_22 IS '賞与控除項目22';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_23 IS '賞与控除項目23';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_24 IS '賞与控除項目24';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_25 IS '賞与控除項目25';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_26 IS '賞与控除項目26';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_27 IS '賞与控除項目27';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_28 IS '賞与控除項目28';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_29 IS '賞与控除項目29';
COMMENT ON COLUMN prd_bonus_parameter_calc.bonus_deduction_item_30 IS '賞与控除項目30';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_1 IS '計算控除項目1';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_2 IS '計算控除項目2';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_3 IS '計算控除項目3';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_4 IS '計算控除項目4';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_5 IS '計算控除項目5';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_6 IS '計算控除項目6';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_7 IS '計算控除項目7';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_8 IS '計算控除項目8';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_9 IS '計算控除項目9';
COMMENT ON COLUMN prd_bonus_parameter_calc.calc_deduction_item_10 IS '計算控除項目10';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_1 IS '変動控除項目1';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_2 IS '変動控除項目2';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_3 IS '変動控除項目3';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_4 IS '変動控除項目4';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_5 IS '変動控除項目5';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_6 IS '変動控除項目6';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_7 IS '変動控除項目7';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_8 IS '変動控除項目8';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_9 IS '変動控除項目9';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_10 IS '変動控除項目10';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_11 IS '変動控除項目11';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_12 IS '変動控除項目12';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_13 IS '変動控除項目13';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_14 IS '変動控除項目14';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_15 IS '変動控除項目15';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_16 IS '変動控除項目16';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_17 IS '変動控除項目17';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_18 IS '変動控除項目18';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_19 IS '変動控除項目19';
COMMENT ON COLUMN prd_bonus_parameter_calc.change_deduction_item_20 IS '変動控除項目20';
COMMENT ON COLUMN prd_bonus_parameter_calc.adjustment_excessive IS '年調過不足額';
COMMENT ON COLUMN prd_bonus_parameter_calc.deduction_total IS '控除合計';
COMMENT ON COLUMN prd_bonus_parameter_calc.balance_pay_price IS '差引支給額';
COMMENT ON COLUMN prd_bonus_parameter_calc.account_price_1 IS '口座１振込額';
COMMENT ON COLUMN prd_bonus_parameter_calc.account_price_2 IS '口座２振込額';
COMMENT ON COLUMN prd_bonus_parameter_calc.account_price_3 IS '口座３振込額';
COMMENT ON COLUMN prd_bonus_parameter_calc.cash_pay_price IS '現金支給額';
COMMENT ON COLUMN prd_bonus_parameter_calc.yearly_total_pay_price IS '年間総支給額';
COMMENT ON COLUMN prd_bonus_parameter_calc.yearly_withholding_price IS '年間源泉対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.yearly_no_tax_price IS '年間非課税対象額';
COMMENT ON COLUMN prd_bonus_parameter_calc.yearly_social_price IS '年間社会保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.yearly_income_tax_price IS '年間所得税';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_employment_insurance IS '事業主雇用保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_accident_insurance IS '事業主労災保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_adjustment_employment_insurance IS '事業主調整雇用保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_adjustment_accident_insurance IS '事業主調整労災保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_health_insurance IS '事業主健康保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_care_insurance IS '事業主介護保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_employee_price IS '事業主厚生年金';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_fund_price IS '事業主厚生年金基金';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_child_price IS '事業主児童手当拠出金';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_health_basic_insurance IS '事業主健保基本保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_health_specific_insurance IS '事業主健保特定保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_adjustment_health_insurance IS '事業主調整健康保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_adjustment_care_insurance IS '事業主調整介護保険料';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_adjustment_employee_price IS '事業主調整厚生年金';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_adjustment_fund_price IS '事業主調整厚生年金基金';
COMMENT ON COLUMN prd_bonus_parameter_calc.owner_adjustment_child_price IS '事業主調整児童手当拠出金';
COMMENT ON COLUMN prd_bonus_parameter_calc.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_bonus_parameter_calc.insert_date IS '登録日';
COMMENT ON COLUMN prd_bonus_parameter_calc.insert_user IS '登録者';
COMMENT ON COLUMN prd_bonus_parameter_calc.update_date IS '更新日';
COMMENT ON COLUMN prd_bonus_parameter_calc.update_user IS '更新者';


CREATE TABLE prd_employee_revision
(
  prd_employee_revision_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  revision_date date NOT NULL, -- 改定年月
  revision_type integer NOT NULL DEFAULT 0, -- 改定区分
  minus_start_date date NOT NULL, -- 引去開始年月
  reward_price integer NOT NULL DEFAULT 0, -- 報酬月額
  grade integer NOT NULL DEFAULT 0, -- 等級
  basic_monthly integer NOT NULL DEFAULT 0, -- 標準月額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_employee_revision_pkey PRIMARY KEY (prd_employee_revision_id )
)
;
COMMENT ON TABLE prd_employee_revision IS '厚生年金改定データ';
COMMENT ON COLUMN prd_employee_revision.prd_employee_revision_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_employee_revision.personal_id IS '個人ID';
COMMENT ON COLUMN prd_employee_revision.revision_date IS '改定年月';
COMMENT ON COLUMN prd_employee_revision.revision_type IS '改定区分';
COMMENT ON COLUMN prd_employee_revision.minus_start_date IS '引去開始年月';
COMMENT ON COLUMN prd_employee_revision.reward_price IS '報酬月額';
COMMENT ON COLUMN prd_employee_revision.grade IS '等級';
COMMENT ON COLUMN prd_employee_revision.basic_monthly IS '標準月額';
COMMENT ON COLUMN prd_employee_revision.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_employee_revision.insert_date IS '登録日';
COMMENT ON COLUMN prd_employee_revision.insert_user IS '登録者';
COMMENT ON COLUMN prd_employee_revision.update_date IS '更新日';
COMMENT ON COLUMN prd_employee_revision.update_user IS '更新者';


CREATE TABLE prd_execute_start_history
(
  prd_execute_start_history_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  work_type integer NOT NULL DEFAULT 1, -- 業務
  execute_date date NOT NULL, -- 処理年月
  execute_times integer NOT NULL, -- 処理回数
  execute_period_from date DEFAULT NULL, -- 対象期間自
  execute_period_to date DEFAULT NULL, -- 対象期間至
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_execute_start_history_pkey PRIMARY KEY (prd_execute_start_history_id )
)
;
COMMENT ON TABLE prd_execute_start_history IS '開始処理履歴データ';
COMMENT ON COLUMN prd_execute_start_history.prd_execute_start_history_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_execute_start_history.company_code IS '会社コード';
COMMENT ON COLUMN prd_execute_start_history.work_type IS '業務';
COMMENT ON COLUMN prd_execute_start_history.execute_date IS '処理年月';
COMMENT ON COLUMN prd_execute_start_history.execute_times IS '処理回数';
COMMENT ON COLUMN prd_execute_start_history.execute_period_from IS '対象期間自';
COMMENT ON COLUMN prd_execute_start_history.execute_period_to IS '対象期間至';
COMMENT ON COLUMN prd_execute_start_history.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_execute_start_history.insert_date IS '登録日';
COMMENT ON COLUMN prd_execute_start_history.insert_user IS '登録者';
COMMENT ON COLUMN prd_execute_start_history.update_date IS '更新日';
COMMENT ON COLUMN prd_execute_start_history.update_user IS '更新者';


CREATE TABLE prd_health_revision
(
  prd_health_revision_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  revision_date date NOT NULL, -- 改定年月
  revision_type integer NOT NULL DEFAULT 0, -- 改定区分
  minus_start_date date NOT NULL, -- 引去開始年月
  reward_price integer NOT NULL DEFAULT 0, -- 報酬月額
  grade integer NOT NULL DEFAULT 0, -- 等級
  basic_monthly integer NOT NULL DEFAULT 0, -- 標準月額
  actual_article_price integer NOT NULL DEFAULT 0, -- 現物によるものの額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_health_revision_pkey PRIMARY KEY (prd_health_revision_id)
)
;
COMMENT ON TABLE prd_health_revision IS '健康保険改定データ';
COMMENT ON COLUMN prd_health_revision.prd_health_revision_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_health_revision.personal_id IS '個人ID';
COMMENT ON COLUMN prd_health_revision.revision_date IS '改定年月';
COMMENT ON COLUMN prd_health_revision.revision_type IS '改定区分';
COMMENT ON COLUMN prd_health_revision.minus_start_date IS '引去開始年月';
COMMENT ON COLUMN prd_health_revision.reward_price IS '報酬月額';
COMMENT ON COLUMN prd_health_revision.grade IS '等級';
COMMENT ON COLUMN prd_health_revision.basic_monthly IS '標準月額';
COMMENT ON COLUMN prd_health_revision.actual_article_price IS '現物によるものの額';
COMMENT ON COLUMN prd_health_revision.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_health_revision.insert_date IS '登録日';
COMMENT ON COLUMN prd_health_revision.insert_user IS '登録者';
COMMENT ON COLUMN prd_health_revision.update_date IS '更新日';
COMMENT ON COLUMN prd_health_revision.update_user IS '更新者';


CREATE TABLE prd_payroll_bonus_calc
(
  prd_payroll_bonus_calc_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  execute_date date NOT NULL, -- 処理年月
  execute_times integer NOT NULL DEFAULT 1, -- 処理回数
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  data_correction_type integer NOT NULL DEFAULT 0, -- データ訂正区分
  payment_date date NOT NULL, -- 支給年月日
  employee_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 社員コード
  last_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 姓
  first_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 名
  last_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- カナ姓
  first_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- カナ名
  birth_date date NOT NULL, -- 生年月日
  gender_type integer NOT NULL, -- 性別区分
  section_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 所属コード
  position_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 職位コード
  office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 事業所コード
  health_office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 健保厚年事業所コード
  labor_office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 労働保険事業所コード
  entrance_date date, -- 入社年月日
  retirement_date date, -- 退職年月日
  state_flag integer NOT NULL DEFAULT 0, -- 在職状態区分
  payroll_bonus_system character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与賞与体系
  payroll_type integer NOT NULL DEFAULT 0, -- 給与区分
  tax_table_type integer NOT NULL DEFAULT 1, -- 税表区分
  specific_tax_type integer NOT NULL DEFAULT 1, -- 指定税率区分
  specific_tax_rate double precision NOT NULL DEFAULT 0, -- 指定税率
  specific_tax_price integer NOT NULL DEFAULT 0, -- 指定税額
  widow_type integer NOT NULL DEFAULT 0, -- 寡婦区分
  disability_type integer NOT NULL DEFAULT 0, -- 障害区分
  student_type integer NOT NULL DEFAULT 0, -- 学生区分
  spouse_type integer NOT NULL DEFAULT 0, -- 配偶者区分
  dependent_total integer NOT NULL DEFAULT 0, -- 扶養人数合計
  social_short_labor_type integer NOT NULL DEFAULT 0, -- 社保短時間就労者区分
  insurance_calc_type integer NOT NULL DEFAULT 0, -- 保険料算出区分
  care_calc_type integer NOT NULL DEFAULT 0, -- 介護保険計算区分
  health_calc_type integer NOT NULL DEFAULT 0, -- 健康保険計算区分
  employee_calc_type integer NOT NULL DEFAULT 0, -- 厚生年金計算区分
  fund_calc_type integer NOT NULL, -- 厚年基金計算区分
  childcare_type integer NOT NULL DEFAULT 0, -- 育児休業区分
  elderlycare_type integer NOT NULL DEFAULT 0, -- 介護休業区分
  childbearing_type integer NOT NULL DEFAULT 0, -- 出産休業区分
  employment_calc_type integer NOT NULL DEFAULT 0, -- 雇用保険計算区分
  employment_age_type integer NOT NULL DEFAULT 0, -- 雇用保険高年齢区分
  accident_calc_type integer NOT NULL DEFAULT 1, -- 労災保険計算区分
  city_code character varying(6) NOT NULL DEFAULT ''::character varying, -- 市区町村コード
  compile_code character varying(6) NOT NULL DEFAULT ''::character varying, -- 住民税納付先コード
  withholding_price integer NOT NULL DEFAULT 0, -- 源泉対象額
  tax_adjustment_price integer NOT NULL DEFAULT 0, -- 課税調整額
  social_total_price integer NOT NULL DEFAULT 0, -- 社会保険料計
  income_tax integer NOT NULL DEFAULT 0, -- 所得税
  payroll_message_code integer NOT NULL DEFAULT 0, -- メッセージコード
  payroll_message character varying(60) NOT NULL DEFAULT 0, -- メッセージ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_payroll_bonus_calc_pkey PRIMARY KEY (prd_payroll_bonus_calc_id )
)
;
COMMENT ON TABLE prd_payroll_bonus_calc IS '給与賞与計算データ';
COMMENT ON COLUMN prd_payroll_bonus_calc.prd_payroll_bonus_calc_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_payroll_bonus_calc.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.execute_date IS '処理年月';
COMMENT ON COLUMN prd_payroll_bonus_calc.execute_times IS '処理回数';
COMMENT ON COLUMN prd_payroll_bonus_calc.personal_id IS '個人ID';
COMMENT ON COLUMN prd_payroll_bonus_calc.data_correction_type IS 'データ訂正区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.payment_date IS '支給年月日';
COMMENT ON COLUMN prd_payroll_bonus_calc.employee_code IS '社員コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.last_name IS '姓';
COMMENT ON COLUMN prd_payroll_bonus_calc.first_name IS '名';
COMMENT ON COLUMN prd_payroll_bonus_calc.last_kana IS 'カナ姓';
COMMENT ON COLUMN prd_payroll_bonus_calc.first_kana IS 'カナ名';
COMMENT ON COLUMN prd_payroll_bonus_calc.birth_date IS '生年月日';
COMMENT ON COLUMN prd_payroll_bonus_calc.gender_type IS '性別区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.section_code IS '所属コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.position_code IS '職位コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.office_code IS '事業所コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.health_office_code IS '健保厚年事業所コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.labor_office_code IS '労働保険事業所コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.entrance_date IS '入社年月日';
COMMENT ON COLUMN prd_payroll_bonus_calc.retirement_date IS '退職年月日';
COMMENT ON COLUMN prd_payroll_bonus_calc.state_flag IS '在職状態区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.payroll_bonus_system IS '給与賞与体系';
COMMENT ON COLUMN prd_payroll_bonus_calc.payroll_type IS '給与区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.tax_table_type IS '税表区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.specific_tax_type IS '指定税率区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.specific_tax_rate IS '指定税率';
COMMENT ON COLUMN prd_payroll_bonus_calc.specific_tax_price IS '指定税額';
COMMENT ON COLUMN prd_payroll_bonus_calc.widow_type IS '寡婦区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.disability_type IS '障害区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.student_type IS '学生区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.spouse_type IS '配偶者区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.dependent_total IS '扶養人数合計';
COMMENT ON COLUMN prd_payroll_bonus_calc.social_short_labor_type IS '社保短時間就労者区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.insurance_calc_type IS '保険料算出区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.care_calc_type IS '介護保険計算区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.health_calc_type IS '健康保険計算区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.employee_calc_type IS '厚生年金計算区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.fund_calc_type IS '厚年基金計算区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.childcare_type IS '育児休業区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.elderlycare_type IS '介護休業区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.childbearing_type IS '出産休業区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.employment_calc_type IS '雇用保険計算区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.employment_age_type IS '雇用保険高年齢区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.accident_calc_type IS '労災保険計算区分';
COMMENT ON COLUMN prd_payroll_bonus_calc.city_code IS '市区町村コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.compile_code IS '住民税納付先コード';
COMMENT ON COLUMN prd_payroll_bonus_calc.withholding_price IS '源泉対象額';
COMMENT ON COLUMN prd_payroll_bonus_calc.tax_adjustment_price IS '課税調整額';
COMMENT ON COLUMN prd_payroll_bonus_calc.social_total_price IS '社会保険料計';
COMMENT ON COLUMN prd_payroll_bonus_calc.income_tax IS '所得税';
COMMENT ON COLUMN prd_payroll_bonus_calc.payroll_message_code IS 'メッセージコード';
COMMENT ON COLUMN prd_payroll_bonus_calc.payroll_message IS 'メッセージ';
COMMENT ON COLUMN prd_payroll_bonus_calc.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_payroll_bonus_calc.insert_date IS '登録日';
COMMENT ON COLUMN prd_payroll_bonus_calc.insert_user IS '登録者';
COMMENT ON COLUMN prd_payroll_bonus_calc.update_date IS '更新日';
COMMENT ON COLUMN prd_payroll_bonus_calc.update_user IS '更新者';


CREATE TABLE prd_payroll_bonus_detail
(
  prd_payroll_bonus_detail_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  execute_date date NOT NULL, -- 処理年月
  execute_times integer DEFAULT 1, -- 処理回数
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  item_value_101 character varying(15) NOT NULL DEFAULT '0', -- 項目内容101
  item_value_102 character varying(15) NOT NULL DEFAULT '0', -- 項目内容102
  item_value_103 character varying(15) NOT NULL DEFAULT '0', -- 項目内容103
  item_value_104 character varying(15) NOT NULL DEFAULT '0', -- 項目内容104
  item_value_105 character varying(15) NOT NULL DEFAULT '0', -- 項目内容105
  item_value_106 character varying(15) NOT NULL DEFAULT '0', -- 項目内容106
  item_value_107 character varying(15) NOT NULL DEFAULT '0', -- 項目内容107
  item_value_108 character varying(15) NOT NULL DEFAULT '0', -- 項目内容108
  item_value_109 character varying(15) NOT NULL DEFAULT '0', -- 項目内容109
  item_value_110 character varying(15) NOT NULL DEFAULT '0', -- 項目内容110
  item_value_111 character varying(15) NOT NULL DEFAULT '0', -- 項目内容111
  item_value_112 character varying(15) NOT NULL DEFAULT '0', -- 項目内容112
  item_value_113 character varying(15) NOT NULL DEFAULT '0', -- 項目内容113
  item_value_114 character varying(15) NOT NULL DEFAULT '0', -- 項目内容114
  item_value_115 character varying(15) NOT NULL DEFAULT '0', -- 項目内容115
  item_value_116 character varying(15) NOT NULL DEFAULT '0', -- 項目内容116
  item_value_117 character varying(15) NOT NULL DEFAULT '0', -- 項目内容117
  item_value_118 character varying(15) NOT NULL DEFAULT '0', -- 項目内容118
  item_value_119 character varying(15) NOT NULL DEFAULT '0', -- 項目内容119
  item_value_120 character varying(15) NOT NULL DEFAULT '0', -- 項目内容120
  item_value_121 character varying(15) NOT NULL DEFAULT '0', -- 項目内容121
  item_value_122 character varying(15) NOT NULL DEFAULT '0', -- 項目内容122
  item_value_123 character varying(15) NOT NULL DEFAULT '0', -- 項目内容123
  item_value_124 character varying(15) NOT NULL DEFAULT '0', -- 項目内容124
  item_value_125 character varying(15) NOT NULL DEFAULT '0', -- 項目内容125
  item_value_126 character varying(15) NOT NULL DEFAULT '0', -- 項目内容126
  item_value_127 character varying(15) NOT NULL DEFAULT '0', -- 項目内容127
  item_value_128 character varying(15) NOT NULL DEFAULT '0', -- 項目内容128
  item_value_129 character varying(15) NOT NULL DEFAULT '0', -- 項目内容129
  item_value_130 character varying(15) NOT NULL DEFAULT '0', -- 項目内容130
  item_value_201 integer NOT NULL DEFAULT 0, -- 項目内容201
  item_value_202 integer NOT NULL DEFAULT 0, -- 項目内容202
  item_value_203 integer NOT NULL DEFAULT 0, -- 項目内容203
  item_value_204 integer NOT NULL DEFAULT 0, -- 項目内容204
  item_value_205 integer NOT NULL DEFAULT 0, -- 項目内容205
  item_value_206 integer NOT NULL DEFAULT 0, -- 項目内容206
  item_value_207 integer NOT NULL DEFAULT 0, -- 項目内容207
  item_value_208 integer NOT NULL DEFAULT 0, -- 項目内容208
  item_value_209 integer NOT NULL DEFAULT 0, -- 項目内容209
  item_value_210 integer NOT NULL DEFAULT 0, -- 項目内容210
  item_value_211 integer NOT NULL DEFAULT 0, -- 項目内容211
  item_value_212 integer NOT NULL DEFAULT 0, -- 項目内容212
  item_value_213 integer NOT NULL DEFAULT 0, -- 項目内容213
  item_value_214 integer NOT NULL DEFAULT 0, -- 項目内容214
  item_value_215 integer NOT NULL DEFAULT 0, -- 項目内容215
  item_value_216 integer NOT NULL DEFAULT 0, -- 項目内容216
  item_value_217 integer NOT NULL DEFAULT 0, -- 項目内容217
  item_value_218 integer NOT NULL DEFAULT 0, -- 項目内容218
  item_value_219 integer NOT NULL DEFAULT 0, -- 項目内容219
  item_value_220 integer NOT NULL DEFAULT 0, -- 項目内容220
  item_value_221 integer NOT NULL DEFAULT 0, -- 項目内容221
  item_value_222 integer NOT NULL DEFAULT 0, -- 項目内容222
  item_value_223 integer NOT NULL DEFAULT 0, -- 項目内容223
  item_value_224 integer NOT NULL DEFAULT 0, -- 項目内容224
  item_value_225 integer NOT NULL DEFAULT 0, -- 項目内容225
  item_value_226 integer NOT NULL DEFAULT 0, -- 項目内容226
  item_value_227 integer NOT NULL DEFAULT 0, -- 項目内容227
  item_value_228 integer NOT NULL DEFAULT 0, -- 項目内容228
  item_value_229 integer NOT NULL DEFAULT 0, -- 項目内容229
  item_value_230 integer NOT NULL DEFAULT 0, -- 項目内容230
  item_value_231 integer NOT NULL DEFAULT 0, -- 項目内容231
  item_value_232 integer NOT NULL DEFAULT 0, -- 項目内容232
  item_value_233 integer NOT NULL DEFAULT 0, -- 項目内容233
  item_value_234 integer NOT NULL DEFAULT 0, -- 項目内容234
  item_value_235 integer NOT NULL DEFAULT 0, -- 項目内容235
  item_value_236 integer NOT NULL DEFAULT 0, -- 項目内容236
  item_value_237 integer NOT NULL DEFAULT 0, -- 項目内容237
  item_value_238 integer NOT NULL DEFAULT 0, -- 項目内容238
  item_value_239 integer NOT NULL DEFAULT 0, -- 項目内容239
  item_value_240 integer NOT NULL DEFAULT 0, -- 項目内容240
  item_value_301 integer NOT NULL DEFAULT 0, -- 項目内容301
  item_value_302 integer NOT NULL DEFAULT 0, -- 項目内容302
  item_value_303 integer NOT NULL DEFAULT 0, -- 項目内容303
  item_value_304 integer NOT NULL DEFAULT 0, -- 項目内容304
  item_value_305 integer NOT NULL DEFAULT 0, -- 項目内容305
  item_value_306 integer NOT NULL DEFAULT 0, -- 項目内容306
  item_value_307 integer NOT NULL DEFAULT 0, -- 項目内容307
  item_value_308 integer NOT NULL DEFAULT 0, -- 項目内容308
  item_value_309 integer NOT NULL DEFAULT 0, -- 項目内容309
  item_value_310 integer NOT NULL DEFAULT 0, -- 項目内容310
  item_value_311 integer NOT NULL DEFAULT 0, -- 項目内容311
  item_value_312 integer NOT NULL DEFAULT 0, -- 項目内容312
  item_value_313 integer NOT NULL DEFAULT 0, -- 項目内容313
  item_value_314 integer NOT NULL DEFAULT 0, -- 項目内容314
  item_value_315 integer NOT NULL DEFAULT 0, -- 項目内容315
  item_value_316 integer NOT NULL DEFAULT 0, -- 項目内容316
  item_value_317 integer NOT NULL DEFAULT 0, -- 項目内容317
  item_value_318 integer NOT NULL DEFAULT 0, -- 項目内容318
  item_value_319 integer NOT NULL DEFAULT 0, -- 項目内容319
  item_value_320 integer NOT NULL DEFAULT 0, -- 項目内容320
  item_value_321 integer NOT NULL DEFAULT 0, -- 項目内容321
  item_value_322 integer NOT NULL DEFAULT 0, -- 項目内容322
  item_value_323 integer NOT NULL DEFAULT 0, -- 項目内容323
  item_value_324 integer NOT NULL DEFAULT 0, -- 項目内容324
  item_value_325 integer NOT NULL DEFAULT 0, -- 項目内容325
  item_value_326 integer NOT NULL DEFAULT 0, -- 項目内容326
  item_value_327 integer NOT NULL DEFAULT 0, -- 項目内容327
  item_value_328 integer NOT NULL DEFAULT 0, -- 項目内容328
  item_value_329 integer NOT NULL DEFAULT 0, -- 項目内容329
  item_value_330 integer NOT NULL DEFAULT 0, -- 項目内容330
  item_value_331 integer NOT NULL DEFAULT 0, -- 項目内容331
  item_value_332 integer NOT NULL DEFAULT 0, -- 項目内容332
  item_value_333 integer NOT NULL DEFAULT 0, -- 項目内容333
  item_value_334 integer NOT NULL DEFAULT 0, -- 項目内容334
  item_value_335 integer NOT NULL DEFAULT 0, -- 項目内容335
  item_value_336 integer NOT NULL DEFAULT 0, -- 項目内容336
  item_value_337 integer NOT NULL DEFAULT 0, -- 項目内容337
  item_value_338 integer NOT NULL DEFAULT 0, -- 項目内容338
  item_value_339 integer NOT NULL DEFAULT 0, -- 項目内容339
  item_value_340 integer NOT NULL DEFAULT 0, -- 項目内容340
  item_value_501 integer NOT NULL DEFAULT 0, -- 項目内容501
  item_value_502 integer NOT NULL DEFAULT 0, -- 項目内容502
  item_value_503 integer NOT NULL DEFAULT 0, -- 項目内容503
  item_value_504 integer NOT NULL DEFAULT 0, -- 項目内容504
  item_value_505 integer NOT NULL DEFAULT 0, -- 項目内容505
  item_value_506 integer NOT NULL DEFAULT 0, -- 項目内容506
  item_value_507 integer NOT NULL DEFAULT 0, -- 項目内容507
  item_value_508 integer NOT NULL DEFAULT 0, -- 項目内容508
  item_value_509 integer NOT NULL DEFAULT 0, -- 項目内容509
  item_value_510 integer NOT NULL DEFAULT 0, -- 項目内容510
  item_value_601 integer NOT NULL DEFAULT 0, -- 項目内容601
  item_value_602 integer NOT NULL DEFAULT 0, -- 項目内容602
  item_value_603 integer NOT NULL DEFAULT 0, -- 項目内容603
  item_value_604 integer NOT NULL DEFAULT 0, -- 項目内容604
  item_value_605 integer NOT NULL DEFAULT 0, -- 項目内容605
  item_value_606 integer NOT NULL DEFAULT 0, -- 項目内容606
  item_value_607 integer NOT NULL DEFAULT 0, -- 項目内容607
  item_value_608 integer NOT NULL DEFAULT 0, -- 項目内容608
  item_value_609 integer NOT NULL DEFAULT 0, -- 項目内容609
  item_value_610 integer NOT NULL DEFAULT 0, -- 項目内容610
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_payroll_bonus_detail_pkey PRIMARY KEY (prd_payroll_bonus_detail_id )
)
;
COMMENT ON TABLE prd_payroll_bonus_detail IS '給与賞与明細書データ';
COMMENT ON COLUMN prd_payroll_bonus_detail.prd_payroll_bonus_detail_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_payroll_bonus_detail.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prd_payroll_bonus_detail.execute_date IS '処理年月';
COMMENT ON COLUMN prd_payroll_bonus_detail.execute_times IS '処理回数';
COMMENT ON COLUMN prd_payroll_bonus_detail.personal_id IS '個人ID';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_101 IS '項目内容101';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_102 IS '項目内容102';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_103 IS '項目内容103';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_104 IS '項目内容104';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_105 IS '項目内容105';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_106 IS '項目内容106';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_107 IS '項目内容107';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_108 IS '項目内容108';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_109 IS '項目内容109';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_110 IS '項目内容110';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_111 IS '項目内容111';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_112 IS '項目内容112';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_113 IS '項目内容113';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_114 IS '項目内容114';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_115 IS '項目内容115';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_116 IS '項目内容116';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_117 IS '項目内容117';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_118 IS '項目内容118';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_119 IS '項目内容119';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_120 IS '項目内容120';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_121 IS '項目内容121';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_122 IS '項目内容122';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_123 IS '項目内容123';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_124 IS '項目内容124';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_125 IS '項目内容125';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_126 IS '項目内容126';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_127 IS '項目内容127';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_128 IS '項目内容128';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_129 IS '項目内容129';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_130 IS '項目内容130';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_201 IS '項目内容201';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_202 IS '項目内容202';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_203 IS '項目内容203';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_204 IS '項目内容204';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_205 IS '項目内容205';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_206 IS '項目内容206';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_207 IS '項目内容207';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_208 IS '項目内容208';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_209 IS '項目内容209';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_210 IS '項目内容210';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_211 IS '項目内容211';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_212 IS '項目内容212';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_213 IS '項目内容213';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_214 IS '項目内容214';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_215 IS '項目内容215';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_216 IS '項目内容216';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_217 IS '項目内容217';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_218 IS '項目内容218';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_219 IS '項目内容219';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_220 IS '項目内容220';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_221 IS '項目内容221';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_222 IS '項目内容222';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_223 IS '項目内容223';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_224 IS '項目内容224';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_225 IS '項目内容225';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_226 IS '項目内容226';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_227 IS '項目内容227';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_228 IS '項目内容228';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_229 IS '項目内容229';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_230 IS '項目内容230';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_231 IS '項目内容231';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_232 IS '項目内容232';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_233 IS '項目内容233';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_234 IS '項目内容234';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_235 IS '項目内容235';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_236 IS '項目内容236';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_237 IS '項目内容237';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_238 IS '項目内容238';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_239 IS '項目内容239';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_240 IS '項目内容240';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_301 IS '項目内容301';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_302 IS '項目内容302';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_303 IS '項目内容303';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_304 IS '項目内容304';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_305 IS '項目内容305';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_306 IS '項目内容306';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_307 IS '項目内容307';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_308 IS '項目内容308';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_309 IS '項目内容309';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_310 IS '項目内容310';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_311 IS '項目内容311';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_312 IS '項目内容312';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_313 IS '項目内容313';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_314 IS '項目内容314';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_315 IS '項目内容315';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_316 IS '項目内容316';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_317 IS '項目内容317';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_318 IS '項目内容318';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_319 IS '項目内容319';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_320 IS '項目内容320';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_321 IS '項目内容321';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_322 IS '項目内容322';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_323 IS '項目内容323';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_324 IS '項目内容324';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_325 IS '項目内容325';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_326 IS '項目内容326';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_327 IS '項目内容327';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_328 IS '項目内容328';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_329 IS '項目内容329';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_330 IS '項目内容330';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_331 IS '項目内容331';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_332 IS '項目内容332';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_333 IS '項目内容333';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_334 IS '項目内容334';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_335 IS '項目内容335';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_336 IS '項目内容336';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_337 IS '項目内容337';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_338 IS '項目内容338';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_339 IS '項目内容339';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_340 IS '項目内容340';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_501 IS '項目内容501';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_502 IS '項目内容502';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_503 IS '項目内容503';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_504 IS '項目内容504';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_505 IS '項目内容505';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_506 IS '項目内容506';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_507 IS '項目内容507';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_508 IS '項目内容508';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_509 IS '項目内容509';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_510 IS '項目内容510';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_601 IS '項目内容601';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_602 IS '項目内容602';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_603 IS '項目内容603';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_604 IS '項目内容604';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_605 IS '項目内容605';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_606 IS '項目内容606';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_607 IS '項目内容607';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_608 IS '項目内容608';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_609 IS '項目内容609';
COMMENT ON COLUMN prd_payroll_bonus_detail.item_value_610 IS '項目内容610';
COMMENT ON COLUMN prd_payroll_bonus_detail.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_payroll_bonus_detail.insert_date IS '登録日';
COMMENT ON COLUMN prd_payroll_bonus_detail.insert_user IS '登録者';
COMMENT ON COLUMN prd_payroll_bonus_detail.update_date IS '更新日';
COMMENT ON COLUMN prd_payroll_bonus_detail.update_user IS '更新者';


CREATE TABLE prd_payroll_bonus_transfer
(
  prd_payroll_bonus_transfer_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  payroll_system character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与体系
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  transfer_date date NOT NULL, -- 振込日
  exchange_code character varying(2) NOT NULL DEFAULT ''::character varying, -- 仕向コード
  suffer_exchange_bank_code character varying(4) NOT NULL DEFAULT ''::character varying, -- 被仕向銀行コード
  suffer_exchange_branch_code character varying(3) NOT NULL DEFAULT ''::character varying, -- 被仕向支店コード
  deposit_type character varying(1) NOT NULL DEFAULT ''::character varying, -- 預金種別
  account_number character varying(7) NOT NULL DEFAULT ''::character varying, -- 口座番号
  serial_number integer NOT NULL DEFAULT 1, -- 連番
  account_kana character varying(30) NOT NULL DEFAULT ''::character varying, -- 口座名義カナ
  account_name character varying(16) NOT NULL DEFAULT ''::character varying, -- 口座名義漢字
  transfer_price integer NOT NULL DEFAULT 0, -- 振込額
  error_type integer NOT NULL DEFAULT 0, -- エラーコード
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_payroll_bonus_transfer_pkey PRIMARY KEY (prd_payroll_bonus_transfer_id )
)
;
COMMENT ON TABLE prd_payroll_bonus_transfer IS '給与賞与振込基礎データ';
COMMENT ON COLUMN prd_payroll_bonus_transfer.prd_payroll_bonus_transfer_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_payroll_bonus_transfer.company_code IS '会社コード';
COMMENT ON COLUMN prd_payroll_bonus_transfer.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prd_payroll_bonus_transfer.payroll_system IS '給与体系';
COMMENT ON COLUMN prd_payroll_bonus_transfer.personal_id IS '個人ID';
COMMENT ON COLUMN prd_payroll_bonus_transfer.transfer_date IS '振込日';
COMMENT ON COLUMN prd_payroll_bonus_transfer.exchange_code IS '仕向コード';
COMMENT ON COLUMN prd_payroll_bonus_transfer.suffer_exchange_bank_code IS '被仕向銀行コード';
COMMENT ON COLUMN prd_payroll_bonus_transfer.suffer_exchange_branch_code IS '被仕向支店コード';
COMMENT ON COLUMN prd_payroll_bonus_transfer.deposit_type IS '預金種別';
COMMENT ON COLUMN prd_payroll_bonus_transfer.account_number IS '口座番号';
COMMENT ON COLUMN prd_payroll_bonus_transfer.serial_number IS '連番';
COMMENT ON COLUMN prd_payroll_bonus_transfer.account_kana IS '口座名義カナ';
COMMENT ON COLUMN prd_payroll_bonus_transfer.account_name IS '口座名義漢字';
COMMENT ON COLUMN prd_payroll_bonus_transfer.transfer_price IS '振込額';
COMMENT ON COLUMN prd_payroll_bonus_transfer.error_type IS 'エラーコード';
COMMENT ON COLUMN prd_payroll_bonus_transfer.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_payroll_bonus_transfer.insert_date IS '登録日';
COMMENT ON COLUMN prd_payroll_bonus_transfer.insert_user IS '登録者';
COMMENT ON COLUMN prd_payroll_bonus_transfer.update_date IS '更新日';
COMMENT ON COLUMN prd_payroll_bonus_transfer.update_user IS '更新者';


CREATE TABLE prd_payroll_change_calc
(
  prd_payroll_change_calc_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  payroll_change_period date NOT NULL, -- 月変算定期間自
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  data_collection_type integer NOT NULL DEFAULT 0, -- データ訂正区分
  employee_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 社員コード
  last_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 姓
  first_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 名
  last_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- カナ姓
  first_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- カナ名
  birth_day date NOT NULL, -- 生年月日
  gender_type integer NOT NULL, -- 性別区分
  section_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 所属コード
  position_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 職位コード
  office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 事業所コード
  health_ofice_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 健保厚年事業所コード
  entrance_date date NOT NULL, -- 入社年月日
  retirement_date date, -- 退職年月日
  state_flag integer NOT NULL DEFAULT 1, -- 在職状態区分
  payroll_bonus_system character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与賞与体系
  social_short_labor_type integer NOT NULL DEFAULT 0, -- 社保短時間就労者区分
  oldperson_employee_type integer NOT NULL DEFAULT 0, -- 高齢被用者区分
  insurance_calc_type integer NOT NULL DEFAULT 0, -- 保険料算出区分
  care_calc_type integer NOT NULL DEFAULT 0, -- 介護保険計算区分
  health_calc_type integer NOT NULL DEFAULT 0, -- 健康保険計算区分
  employee_calc_type integer NOT NULL DEFAULT 0, -- 厚生年金計算区分
  health_card_number character varying(7) DEFAULT ''::character varying, -- 健康保険証番号
  employee_type integer NOT NULL DEFAULT 1, -- 厚生年金種別
  employee_number character varying(10) DEFAULT ''::character varying, -- 厚生年金番号
  fund_number character varying(10) DEFAULT ''::character varying, -- 厚年基金加入者番号
  calc_type integer NOT NULL DEFAULT 0, -- 計算種類
  insurance_change_type integer NOT NULL DEFAULT 0, -- 保険者算定区分
  health_current_change_date date, -- 健保現改定年月
  health_grade integer NOT NULL DEFAULT 0, -- 健保現等級
  health_basic_reward integer NOT NULL DEFAULT 0, -- 健保現標準報酬
  health_reward_price integer NOT NULL DEFAULT 0, -- 健保現報酬月額
  employee_current_change_date date, -- 厚年現改定年月
  employee_grade integer NOT NULL DEFAULT 0, -- 厚年現等級
  employee_basic_reword integer NOT NULL DEFAULT 0, -- 厚年現標準報酬
  employee_reword_price integer NOT NULL DEFAULT 0, -- 厚年現報酬月額
  fixed_price_0 integer NOT NULL DEFAULT 0, -- 固定額0
  transport_price_0 integer NOT NULL DEFAULT 0, -- 通勤費0
  transport_actual_0 integer NOT NULL DEFAULT 0, -- 通勤費現物0
  payroll_change_type integer NOT NULL DEFAULT 0, -- 固定的賃金変動区分
  payroll_change_state_1 integer NOT NULL DEFAULT 0, -- 固定賃金変動状態1
  payroll_change_state_2 integer NOT NULL DEFAULT 0, -- 固定賃金変動状態2
  social_type_1 integer NOT NULL DEFAULT 0, -- 社会保険対象区分1
  social_type_2 integer NOT NULL DEFAULT 0, -- 社会保険対象区分2
  social_type_3 integer NOT NULL DEFAULT 0, -- 社会保険対象区分3
  social_short_type_1 integer NOT NULL DEFAULT 0, -- 社会保険短時間区分1
  social_short_type_2 integer NOT NULL DEFAULT 0, -- 社会保険短時間区分2
  social_short_type_3 integer NOT NULL DEFAULT 0, -- 社会保険短時間区分3
  basic_day_1 integer NOT NULL DEFAULT 0, -- 基礎日数1
  basic_day_2 integer NOT NULL DEFAULT 0, -- 基礎日数2
  basic_day_3 integer NOT NULL DEFAULT 0, -- 基礎日数3
  fixed_price_1 integer NOT NULL DEFAULT 0, -- 固定額1
  fixed_price_2 integer NOT NULL DEFAULT 0, -- 固定額2
  fixed_price_3 integer NOT NULL DEFAULT 0, -- 固定額3
  change_price_1 integer NOT NULL DEFAULT 0, -- 変動額1
  change_price_2 integer NOT NULL DEFAULT 0, -- 変動額2
  change_price_3 integer NOT NULL DEFAULT 0, -- 変動額3
  add_price_1 integer NOT NULL DEFAULT 0, -- 加算額1
  add_price_2 integer NOT NULL DEFAULT 0, -- 加算額2
  add_price_3 integer NOT NULL DEFAULT 0, -- 加算額3
  transport_price_1 integer NOT NULL DEFAULT 0, -- 通勤費1
  transport_price_2 integer NOT NULL DEFAULT 0, -- 通勤費2
  transport_price_3 integer NOT NULL DEFAULT 0, -- 通勤費3
  transport_actual_1 integer NOT NULL DEFAULT 0, -- 通勤費現物1
  transport_actual_2 integer NOT NULL DEFAULT 0, -- 通勤費現物2
  transport_actual_3 integer NOT NULL DEFAULT 0, -- 通勤費現物3
  actual_pay_1 integer NOT NULL DEFAULT 0, -- 現物支給1
  actual_pay_2 integer NOT NULL DEFAULT 0, -- 現物支給2
  actual_pay_3 integer NOT NULL DEFAULT 0, -- 現物支給3
  health_change_type integer NOT NULL DEFAULT 0, -- 健保月変算定区分
  health_change_date date, -- 健保改定年月
  employee_change_type integer NOT NULL DEFAULT 0, -- 厚年月変算定区分
  employee_change_date date, -- 厚年改定年月
  correction_average_add_price integer NOT NULL DEFAULT 0, -- 修正平均加算額
  retrospective_price integer NOT NULL DEFAULT 0, -- 遡及額
  retrospective_month date, -- 遡及支払月
  lifting_monthly_price integer NOT NULL DEFAULT 0, -- 昇降給差月額
  number_lifting_month integer NOT NULL DEFAULT 0, -- 昇降給差月数
  lifting_date date, -- 昇降給年月
  true_average_price integer NOT NULL DEFAULT 0, -- 単純平均額
  correction_average_price integer NOT NULL DEFAULT 0, -- 修正平均額
  health_new_grade integer NOT NULL DEFAULT 0, -- 健保新等級
  health_new_basic_reward integer NOT NULL DEFAULT 0, -- 健保新標準報酬
  health_new_reward_price integer NOT NULL DEFAULT 0, -- 健保新報酬月額
  employee_new_grade integer NOT NULL DEFAULT 0, -- 厚年新等級
  employee_new_basic_reward integer NOT NULL DEFAULT 0, -- 厚年新標準報酬
  employee_new_reward_price integer NOT NULL DEFAULT 0, -- 厚年新報酬月額
  middle_entrance_date date, -- 途中入社年月日
  suspension_term character varying(50) NOT NULL DEFAULT ''::character varying, -- 休職等期間
  regularly_other_comment character varying(50) NOT NULL DEFAULT ''::character varying, -- 算定その他
  lifting_reason character varying(50) NOT NULL DEFAULT ''::character varying, -- 昇降給理由
  only_health_change_type integer NOT NULL DEFAULT 0, -- 健保のみ月変区分
  monthly_other_comment character varying(50) NOT NULL DEFAULT ''::character varying, -- 月変その他
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_payroll_change_calc_pkey PRIMARY KEY (prd_payroll_change_calc_id )
)
;
COMMENT ON TABLE prd_payroll_change_calc IS '月変算定計算データ';
COMMENT ON COLUMN prd_payroll_change_calc.prd_payroll_change_calc_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_payroll_change_calc.payroll_change_period IS '月変算定期間自';
COMMENT ON COLUMN prd_payroll_change_calc.personal_id IS '個人ID';
COMMENT ON COLUMN prd_payroll_change_calc.data_collection_type IS 'データ訂正区分';
COMMENT ON COLUMN prd_payroll_change_calc.employee_code IS '社員コード';
COMMENT ON COLUMN prd_payroll_change_calc.last_name IS '姓';
COMMENT ON COLUMN prd_payroll_change_calc.first_name IS '名';
COMMENT ON COLUMN prd_payroll_change_calc.last_kana IS 'カナ姓';
COMMENT ON COLUMN prd_payroll_change_calc.first_kana IS 'カナ名';
COMMENT ON COLUMN prd_payroll_change_calc.birth_day IS '生年月日';
COMMENT ON COLUMN prd_payroll_change_calc.gender_type IS '性別区分';
COMMENT ON COLUMN prd_payroll_change_calc.section_code IS '所属コード';
COMMENT ON COLUMN prd_payroll_change_calc.position_code IS '職位コード';
COMMENT ON COLUMN prd_payroll_change_calc.office_code IS '事業所コード';
COMMENT ON COLUMN prd_payroll_change_calc.health_ofice_code IS '健保厚年事業所コード';
COMMENT ON COLUMN prd_payroll_change_calc.entrance_date IS '入社年月日';
COMMENT ON COLUMN prd_payroll_change_calc.retirement_date IS '退職年月日';
COMMENT ON COLUMN prd_payroll_change_calc.state_flag IS '在職状態区分';
COMMENT ON COLUMN prd_payroll_change_calc.payroll_bonus_system IS '給与賞与体系';
COMMENT ON COLUMN prd_payroll_change_calc.social_short_labor_type IS '社保短時間就労者区分';
COMMENT ON COLUMN prd_payroll_change_calc.oldperson_employee_type IS '高齢被用者区分';
COMMENT ON COLUMN prd_payroll_change_calc.insurance_calc_type IS '保険料算出区分';
COMMENT ON COLUMN prd_payroll_change_calc.care_calc_type IS '介護保険計算区分';
COMMENT ON COLUMN prd_payroll_change_calc.health_calc_type IS '健康保険計算区分';
COMMENT ON COLUMN prd_payroll_change_calc.employee_calc_type IS '厚生年金計算区分';
COMMENT ON COLUMN prd_payroll_change_calc.health_card_number IS '健康保険証番号';
COMMENT ON COLUMN prd_payroll_change_calc.employee_type IS '厚生年金種別';
COMMENT ON COLUMN prd_payroll_change_calc.employee_number IS '厚生年金番号';
COMMENT ON COLUMN prd_payroll_change_calc.fund_number IS '厚年基金加入者番号';
COMMENT ON COLUMN prd_payroll_change_calc.calc_type IS '計算種類';
COMMENT ON COLUMN prd_payroll_change_calc.insurance_change_type IS '保険者算定区分';
COMMENT ON COLUMN prd_payroll_change_calc.health_current_change_date IS '健保現改定年月';
COMMENT ON COLUMN prd_payroll_change_calc.health_grade IS '健保現等級';
COMMENT ON COLUMN prd_payroll_change_calc.health_basic_reward IS '健保現標準報酬';
COMMENT ON COLUMN prd_payroll_change_calc.health_reward_price IS '健保現報酬月額';
COMMENT ON COLUMN prd_payroll_change_calc.employee_current_change_date IS '厚年現改定年月';
COMMENT ON COLUMN prd_payroll_change_calc.employee_grade IS '厚年現等級';
COMMENT ON COLUMN prd_payroll_change_calc.employee_basic_reword IS '厚年現標準報酬';
COMMENT ON COLUMN prd_payroll_change_calc.employee_reword_price IS '厚年現報酬月額';
COMMENT ON COLUMN prd_payroll_change_calc.fixed_price_0 IS '固定額0';
COMMENT ON COLUMN prd_payroll_change_calc.transport_price_0 IS '通勤費0';
COMMENT ON COLUMN prd_payroll_change_calc.transport_actual_0 IS '通勤費現物0';
COMMENT ON COLUMN prd_payroll_change_calc.payroll_change_type IS '固定的賃金変動区分';
COMMENT ON COLUMN prd_payroll_change_calc.payroll_change_state_1 IS '固定賃金変動状態1';
COMMENT ON COLUMN prd_payroll_change_calc.payroll_change_state_2 IS '固定賃金変動状態2';
COMMENT ON COLUMN prd_payroll_change_calc.social_type_1 IS '社会保険対象区分1';
COMMENT ON COLUMN prd_payroll_change_calc.social_type_2 IS '社会保険対象区分2';
COMMENT ON COLUMN prd_payroll_change_calc.social_type_3 IS '社会保険対象区分3';
COMMENT ON COLUMN prd_payroll_change_calc.social_short_type_1 IS '社会保険短時間区分1';
COMMENT ON COLUMN prd_payroll_change_calc.social_short_type_2 IS '社会保険短時間区分2';
COMMENT ON COLUMN prd_payroll_change_calc.social_short_type_3 IS '社会保険短時間区分3';
COMMENT ON COLUMN prd_payroll_change_calc.basic_day_1 IS '基礎日数1';
COMMENT ON COLUMN prd_payroll_change_calc.basic_day_2 IS '基礎日数2';
COMMENT ON COLUMN prd_payroll_change_calc.basic_day_3 IS '基礎日数3';
COMMENT ON COLUMN prd_payroll_change_calc.fixed_price_1 IS '固定額1';
COMMENT ON COLUMN prd_payroll_change_calc.fixed_price_2 IS '固定額2';
COMMENT ON COLUMN prd_payroll_change_calc.fixed_price_3 IS '固定額3';
COMMENT ON COLUMN prd_payroll_change_calc.change_price_1 IS '変動額1';
COMMENT ON COLUMN prd_payroll_change_calc.change_price_2 IS '変動額2';
COMMENT ON COLUMN prd_payroll_change_calc.change_price_3 IS '変動額3';
COMMENT ON COLUMN prd_payroll_change_calc.add_price_1 IS '加算額1';
COMMENT ON COLUMN prd_payroll_change_calc.add_price_2 IS '加算額2';
COMMENT ON COLUMN prd_payroll_change_calc.add_price_3 IS '加算額3';
COMMENT ON COLUMN prd_payroll_change_calc.transport_price_1 IS '通勤費1';
COMMENT ON COLUMN prd_payroll_change_calc.transport_price_2 IS '通勤費2';
COMMENT ON COLUMN prd_payroll_change_calc.transport_price_3 IS '通勤費3';
COMMENT ON COLUMN prd_payroll_change_calc.transport_actual_1 IS '通勤費現物1';
COMMENT ON COLUMN prd_payroll_change_calc.transport_actual_2 IS '通勤費現物2';
COMMENT ON COLUMN prd_payroll_change_calc.transport_actual_3 IS '通勤費現物3';
COMMENT ON COLUMN prd_payroll_change_calc.actual_pay_1 IS '現物支給1';
COMMENT ON COLUMN prd_payroll_change_calc.actual_pay_2 IS '現物支給2';
COMMENT ON COLUMN prd_payroll_change_calc.actual_pay_3 IS '現物支給3';
COMMENT ON COLUMN prd_payroll_change_calc.health_change_type IS '健保月変算定区分';
COMMENT ON COLUMN prd_payroll_change_calc.health_change_date IS '健保改定年月';
COMMENT ON COLUMN prd_payroll_change_calc.employee_change_type IS '厚年月変算定区分';
COMMENT ON COLUMN prd_payroll_change_calc.employee_change_date IS '厚年改定年月';
COMMENT ON COLUMN prd_payroll_change_calc.correction_average_add_price IS '修正平均加算額';
COMMENT ON COLUMN prd_payroll_change_calc.retrospective_price IS '遡及額';
COMMENT ON COLUMN prd_payroll_change_calc.retrospective_month IS '遡及支払月';
COMMENT ON COLUMN prd_payroll_change_calc.lifting_monthly_price IS '昇降給差月額';
COMMENT ON COLUMN prd_payroll_change_calc.number_lifting_month IS '昇降給差月数';
COMMENT ON COLUMN prd_payroll_change_calc.lifting_date IS '昇降給年月';
COMMENT ON COLUMN prd_payroll_change_calc.true_average_price IS '単純平均額';
COMMENT ON COLUMN prd_payroll_change_calc.correction_average_price IS '修正平均額';
COMMENT ON COLUMN prd_payroll_change_calc.health_new_grade IS '健保新等級';
COMMENT ON COLUMN prd_payroll_change_calc.health_new_basic_reward IS '健保新標準報酬';
COMMENT ON COLUMN prd_payroll_change_calc.health_new_reward_price IS '健保新報酬月額';
COMMENT ON COLUMN prd_payroll_change_calc.employee_new_grade IS '厚年新等級';
COMMENT ON COLUMN prd_payroll_change_calc.employee_new_basic_reward IS '厚年新標準報酬';
COMMENT ON COLUMN prd_payroll_change_calc.employee_new_reward_price IS '厚年新報酬月額';
COMMENT ON COLUMN prd_payroll_change_calc.middle_entrance_date IS '途中入社年月日';
COMMENT ON COLUMN prd_payroll_change_calc.suspension_term IS '休職等期間';
COMMENT ON COLUMN prd_payroll_change_calc.regularly_other_comment IS '算定その他';
COMMENT ON COLUMN prd_payroll_change_calc.lifting_reason IS '昇降給理由';
COMMENT ON COLUMN prd_payroll_change_calc.only_health_change_type IS '健保のみ月変区分';
COMMENT ON COLUMN prd_payroll_change_calc.monthly_other_comment IS '月変その他';
COMMENT ON COLUMN prd_payroll_change_calc.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prd_payroll_change_calc.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_payroll_change_calc.insert_date IS '登録日';
COMMENT ON COLUMN prd_payroll_change_calc.insert_user IS '登録者';
COMMENT ON COLUMN prd_payroll_change_calc.update_date IS '更新日';
COMMENT ON COLUMN prd_payroll_change_calc.update_user IS '更新者';


CREATE TABLE prd_payroll_parameter_calc
(
  prd_payroll_parameter_calc_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 処理年月
  execute_times integer NOT NULL DEFAULT 1, -- 処理回数
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  day_work double precision NOT NULL DEFAULT 0, -- 出勤日数
  day_absence double precision NOT NULL DEFAULT 0, -- 欠勤日数
  day_paid_holiday double precision NOT NULL DEFAULT 0, -- 有休取得日数
  day_remainder_paid_holiday double precision NOT NULL DEFAULT 0, -- 有休残日数
  day_base_work double precision NOT NULL DEFAULT 0, -- 要勤務日数
  day_times_diem double precision NOT NULL DEFAULT 0, -- 日給者日数
  day_item_1 double precision NOT NULL DEFAULT 0, -- 日数項目1
  day_item_2 double precision NOT NULL DEFAULT 0, -- 日数項目2
  day_item_3 double precision NOT NULL DEFAULT 0, -- 日数項目3
  day_item_4 double precision NOT NULL DEFAULT 0, -- 日数項目4
  day_item_5 double precision NOT NULL DEFAULT 0, -- 日数項目5
  day_item_6 double precision NOT NULL DEFAULT 0, -- 日数項目6
  day_item_7 double precision NOT NULL DEFAULT 0, -- 日数項目7
  day_item_8 double precision NOT NULL DEFAULT 0, -- 日数項目8
  day_item_9 double precision NOT NULL DEFAULT 0, -- 日数項目9
  day_item_10 double precision NOT NULL DEFAULT 0, -- 日数項目10
  hourly_waqe_hour integer NOT NULL DEFAULT 0, -- 時給者時間数
  overtime_hour integer NOT NULL DEFAULT 0, -- 残業時間
  latetime_hour integer NOT NULL DEFAULT 0, -- 深夜残業時間
  legal_overtime_hour integer NOT NULL DEFAULT 0, -- 法定休日残業時間
  legal_latetime_hour integer NOT NULL DEFAULT 0, -- 法定休日深夜残業時間
  specific_overtime_hour integer NOT NULL DEFAULT 0, -- 所定休日残業時間
  specific_latetime_hour integer NOT NULL DEFAULT 0, -- 所定休日深夜残業時間
  hour_45_over_hour integer NOT NULL DEFAULT 0, -- ４５時間越割増時間
  hour_60_over_hour integer NOT NULL DEFAULT 0, -- ６０時間越割増時間
  late_early_hour integer NOT NULL DEFAULT 0, -- 遅刻早退等控除時間
  time_item_1 integer NOT NULL DEFAULT 0, -- 時間項目1
  time_item_2 integer NOT NULL DEFAULT 0, -- 時間項目2
  time_item_3 integer NOT NULL DEFAULT 0, -- 時間項目3
  time_item_4 integer NOT NULL DEFAULT 0, -- 時間項目4
  time_item_5 integer NOT NULL DEFAULT 0, -- 時間項目5
  time_item_6 integer NOT NULL DEFAULT 0, -- 時間項目6
  time_item_7 integer NOT NULL DEFAULT 0, -- 時間項目7
  time_item_8 integer NOT NULL DEFAULT 0, -- 時間項目8
  time_item_9 integer NOT NULL DEFAULT 0, -- 時間項目9
  time_item_10 integer NOT NULL DEFAULT 0, -- 時間項目10
  time_item_11 integer NOT NULL DEFAULT 0, -- 時間項目11
  time_item_12 integer NOT NULL DEFAULT 0, -- 時間項目12
  time_item_13 integer NOT NULL DEFAULT 0, -- 時間項目13
  time_item_14 integer NOT NULL DEFAULT 0, -- 時間項目14
  time_item_15 integer NOT NULL DEFAULT 0, -- 時間項目15
  times_item_1 integer NOT NULL DEFAULT 0, -- 回数項目1
  times_item_2 integer NOT NULL DEFAULT 0, -- 回数項目2
  times_item_3 integer NOT NULL DEFAULT 0, -- 回数項目3
  times_item_4 integer NOT NULL DEFAULT 0, -- 回数項目4
  times_item_5 integer NOT NULL DEFAULT 0, -- 回数項目5
  times_item_6 integer NOT NULL DEFAULT 0, -- 回数項目6
  times_item_7 integer NOT NULL DEFAULT 0, -- 回数項目7
  times_item_8 integer NOT NULL DEFAULT 0, -- 回数項目8
  times_item_9 integer NOT NULL DEFAULT 0, -- 回数項目9
  times_item_10 integer NOT NULL DEFAULT 0, -- 回数項目10
  times_item_11 integer NOT NULL DEFAULT 0, -- 回数項目11
  times_item_12 integer NOT NULL DEFAULT 0, -- 回数項目12
  times_item_13 integer NOT NULL DEFAULT 0, -- 回数項目13
  times_item_14 integer NOT NULL DEFAULT 0, -- 回数項目14
  times_item_15 integer NOT NULL DEFAULT 0, -- 回数項目15
  times_item_16 integer NOT NULL DEFAULT 0, -- 回数項目16
  times_item_17 integer NOT NULL DEFAULT 0, -- 回数項目17
  times_item_18 integer NOT NULL DEFAULT 0, -- 回数項目18
  times_item_19 integer NOT NULL DEFAULT 0, -- 回数項目19
  times_item_20 integer NOT NULL DEFAULT 0, -- 回数項目20
  rate_item_1 double precision NOT NULL DEFAULT 0, -- 倍率項目1
  rate_item_2 double precision NOT NULL DEFAULT 0, -- 倍率項目2
  rate_item_3 double precision NOT NULL DEFAULT 0, -- 倍率項目3
  rate_item_4 double precision NOT NULL DEFAULT 0, -- 倍率項目4
  rate_item_5 double precision NOT NULL DEFAULT 0, -- 倍率項目5
  unit_diem double precision NOT NULL DEFAULT 0, -- 日給単価
  unit_hourly double precision NOT NULL DEFAULT 0, -- 時給単価
  unit_overtime_basic double precision NOT NULL DEFAULT 0, -- 残業基準単価
  unit_overtime double precision NOT NULL DEFAULT 0, -- 残業単価
  unit_latetime double precision NOT NULL DEFAULT 0, -- 深夜残業単価
  unit_legal_overtime double precision NOT NULL DEFAULT 0, -- 法定休日残業単価
  unit_legal_latetime double precision NOT NULL DEFAULT 0, -- 法定休日深夜残業単価
  unit_specific_overtime double precision NOT NULL DEFAULT 0, -- 所定休日残業単価
  unit_specific_latetime double precision NOT NULL DEFAULT 0, -- 所定休日深夜残業単価
  unit_45_hour_over double precision NOT NULL DEFAULT 0, -- ４５時間越割増単価
  unit_60_hour_over double precision NOT NULL DEFAULT 0, -- ６０時間越割増単価
  unit_late_early_basic double precision NOT NULL DEFAULT 0, -- 遅早等控除基準単価
  unit_late_early double precision NOT NULL DEFAULT 0, -- 遅早等控除単価
  unit_absence_basic double precision NOT NULL DEFAULT 0, -- 欠勤控除基準単価
  unit_absence double precision NOT NULL DEFAULT 0, -- 欠勤控除単価
  unit_item_1 double precision NOT NULL DEFAULT 0, -- 単価項目1
  unit_item_2 double precision NOT NULL DEFAULT 0, -- 単価項目2
  unit_item_3 double precision NOT NULL DEFAULT 0, -- 単価項目3
  unit_item_4 double precision NOT NULL DEFAULT 0, -- 単価項目4
  unit_item_5 double precision NOT NULL DEFAULT 0, -- 単価項目5
  unit_item_6 double precision NOT NULL DEFAULT 0, -- 単価項目6
  unit_item_7 double precision NOT NULL DEFAULT 0, -- 単価項目7
  unit_item_8 double precision NOT NULL DEFAULT 0, -- 単価項目8
  unit_item_9 double precision NOT NULL DEFAULT 0, -- 単価項目9
  unit_item_10 double precision NOT NULL DEFAULT 0, -- 単価項目10
  unit_item_11 double precision NOT NULL DEFAULT 0, -- 単価項目11
  unit_item_12 double precision NOT NULL DEFAULT 0, -- 単価項目12
  unit_item_13 double precision NOT NULL DEFAULT 0, -- 単価項目13
  unit_item_14 double precision NOT NULL DEFAULT 0, -- 単価項目14
  unit_item_15 double precision NOT NULL DEFAULT 0, -- 単価項目15
  unit_item_16 double precision NOT NULL DEFAULT 0, -- 単価項目16
  unit_item_17 double precision NOT NULL DEFAULT 0, -- 単価項目17
  unit_item_18 double precision NOT NULL DEFAULT 0, -- 単価項目18
  unit_item_19 double precision NOT NULL DEFAULT 0, -- 単価項目19
  unit_item_20 double precision NOT NULL DEFAULT 0, -- 単価項目20
  payroll_monthly integer NOT NULL DEFAULT 0, -- 月給者基本給
  payroll_diem integer NOT NULL DEFAULT 0, -- 日給者基本給
  payroll_hourly integer NOT NULL DEFAULT 0, -- 時給者基本給
  fixed_pay_item_1 integer NOT NULL DEFAULT 0, -- 固定支給項目1
  fixed_pay_item_2 integer NOT NULL DEFAULT 0, -- 固定支給項目2
  fixed_pay_item_3 integer NOT NULL DEFAULT 0, -- 固定支給項目3
  fixed_pay_item_4 integer NOT NULL DEFAULT 0, -- 固定支給項目4
  fixed_pay_item_5 integer NOT NULL DEFAULT 0, -- 固定支給項目5
  fixed_pay_item_6 integer NOT NULL DEFAULT 0, -- 固定支給項目6
  fixed_pay_item_7 integer NOT NULL DEFAULT 0, -- 固定支給項目7
  fixed_pay_item_8 integer NOT NULL DEFAULT 0, -- 固定支給項目8
  fixed_pay_item_9 integer NOT NULL DEFAULT 0, -- 固定支給項目9
  fixed_pay_item_10 integer NOT NULL DEFAULT 0, -- 固定支給項目10
  fixed_pay_item_11 integer NOT NULL DEFAULT 0, -- 固定支給項目11
  fixed_pay_item_12 integer NOT NULL DEFAULT 0, -- 固定支給項目12
  fixed_pay_item_13 integer NOT NULL DEFAULT 0, -- 固定支給項目13
  fixed_pay_item_14 integer NOT NULL DEFAULT 0, -- 固定支給項目14
  fixed_pay_item_15 integer NOT NULL DEFAULT 0, -- 固定支給項目15
  fixed_pay_item_16 integer NOT NULL DEFAULT 0, -- 固定支給項目16
  fixed_pay_item_17 integer NOT NULL DEFAULT 0, -- 固定支給項目17
  fixed_pay_item_18 integer NOT NULL DEFAULT 0, -- 固定支給項目18
  fixed_pay_item_19 integer NOT NULL DEFAULT 0, -- 固定支給項目19
  fixed_pay_item_20 integer NOT NULL DEFAULT 0, -- 固定支給項目20
  fixed_pay_item_21 integer NOT NULL DEFAULT 0, -- 固定支給項目21
  fixed_pay_item_22 integer NOT NULL DEFAULT 0, -- 固定支給項目22
  fixed_pay_item_23 integer NOT NULL DEFAULT 0, -- 固定支給項目23
  fixed_pay_item_24 integer NOT NULL DEFAULT 0, -- 固定支給項目24
  fixed_pay_item_25 integer NOT NULL DEFAULT 0, -- 固定支給項目25
  fixed_pay_item_26 integer NOT NULL DEFAULT 0, -- 固定支給項目26
  fixed_pay_item_27 integer NOT NULL DEFAULT 0, -- 固定支給項目27
  fixed_pay_item_28 integer NOT NULL DEFAULT 0, -- 固定支給項目28
  fixed_pay_item_29 integer NOT NULL DEFAULT 0, -- 固定支給項目29
  fixed_pay_item_30 integer NOT NULL DEFAULT 0, -- 固定支給項目30
  overtime_allowance integer NOT NULL DEFAULT 0, -- 普通残業手当
  latetime_allowance integer NOT NULL DEFAULT 0, -- 深夜残業手当
  legal_overtime_allowance integer NOT NULL DEFAULT 0, -- 法定休日残業手当
  legal_latetime_allowance integer NOT NULL DEFAULT 0, -- 法定休日深夜残業手当
  specific_overtime_allowance integer NOT NULL DEFAULT 0, -- 所定休日残業手当
  specific_latetime_allowance integer NOT NULL DEFAULT 0, -- 所定休日深夜残業手当
  hour_45_over_allowance integer NOT NULL DEFAULT 0, -- ４５時間越割増手当
  hour_60_over_allowance integer NOT NULL DEFAULT 0, -- ６０時間越割増手当
  late_early_price integer NOT NULL DEFAULT 0, -- 遅早等控除額
  absence_price integer NOT NULL DEFAULT 0, -- 欠勤控除額
  calc_pay_item_1 integer NOT NULL DEFAULT 0, -- 計算支給項目1
  calc_pay_item_2 integer NOT NULL DEFAULT 0, -- 計算支給項目2
  calc_pay_item_3 integer NOT NULL DEFAULT 0, -- 計算支給項目3
  calc_pay_item_4 integer NOT NULL DEFAULT 0, -- 計算支給項目4
  calc_pay_item_5 integer NOT NULL DEFAULT 0, -- 計算支給項目5
  calc_pay_item_6 integer NOT NULL DEFAULT 0, -- 計算支給項目6
  calc_pay_item_7 integer NOT NULL DEFAULT 0, -- 計算支給項目7
  calc_pay_item_8 integer NOT NULL DEFAULT 0, -- 計算支給項目8
  calc_pay_item_9 integer NOT NULL DEFAULT 0, -- 計算支給項目9
  calc_pay_item_10 integer NOT NULL DEFAULT 0, -- 計算支給項目10
  calc_pay_item_11 integer NOT NULL DEFAULT 0, -- 計算支給項目11
  calc_pay_item_12 integer NOT NULL DEFAULT 0, -- 計算支給項目12
  calc_pay_item_13 integer NOT NULL DEFAULT 0, -- 計算支給項目13
  calc_pay_item_14 integer NOT NULL DEFAULT 0, -- 計算支給項目14
  calc_pay_item_15 integer NOT NULL DEFAULT 0, -- 計算支給項目15
  calc_pay_item_16 integer NOT NULL DEFAULT 0, -- 計算支給項目16
  calc_pay_item_17 integer NOT NULL DEFAULT 0, -- 計算支給項目17
  calc_pay_item_18 integer NOT NULL DEFAULT 0, -- 計算支給項目18
  calc_pay_item_19 integer NOT NULL DEFAULT 0, -- 計算支給項目19
  calc_pay_item_20 integer NOT NULL DEFAULT 0, -- 計算支給項目20
  change_pay_item_1 integer NOT NULL DEFAULT 0, -- 変動支給項目1
  change_pay_item_2 integer NOT NULL DEFAULT 0, -- 変動支給項目2
  change_pay_item_3 integer NOT NULL DEFAULT 0, -- 変動支給項目3
  change_pay_item_4 integer NOT NULL DEFAULT 0, -- 変動支給項目4
  change_pay_item_5 integer NOT NULL DEFAULT 0, -- 変動支給項目5
  change_pay_item_6 integer NOT NULL DEFAULT 0, -- 変動支給項目6
  change_pay_item_7 integer NOT NULL DEFAULT 0, -- 変動支給項目7
  change_pay_item_8 integer NOT NULL DEFAULT 0, -- 変動支給項目8
  change_pay_item_9 integer NOT NULL DEFAULT 0, -- 変動支給項目9
  change_pay_item_10 integer NOT NULL DEFAULT 0, -- 変動支給項目10
  change_pay_item_11 integer NOT NULL DEFAULT 0, -- 変動支給項目11
  change_pay_item_12 integer NOT NULL DEFAULT 0, -- 変動支給項目12
  change_pay_item_13 integer NOT NULL DEFAULT 0, -- 変動支給項目13
  change_pay_item_14 integer NOT NULL DEFAULT 0, -- 変動支給項目14
  change_pay_item_15 integer NOT NULL DEFAULT 0, -- 変動支給項目15
  change_pay_item_16 integer NOT NULL DEFAULT 0, -- 変動支給項目16
  change_pay_item_17 integer NOT NULL DEFAULT 0, -- 変動支給項目17
  change_pay_item_18 integer NOT NULL DEFAULT 0, -- 変動支給項目18
  change_pay_item_19 integer NOT NULL DEFAULT 0, -- 変動支給項目19
  change_pay_item_20 integer NOT NULL DEFAULT 0, -- 変動支給項目20
  change_pay_item_21 integer NOT NULL DEFAULT 0, -- 変動支給項目21
  change_pay_item_22 integer NOT NULL DEFAULT 0, -- 変動支給項目22
  change_pay_item_23 integer NOT NULL DEFAULT 0, -- 変動支給項目23
  change_pay_item_24 integer NOT NULL DEFAULT 0, -- 変動支給項目24
  change_pay_item_25 integer NOT NULL DEFAULT 0, -- 変動支給項目25
  change_pay_item_26 integer NOT NULL DEFAULT 0, -- 変動支給項目26
  change_pay_item_27 integer NOT NULL DEFAULT 0, -- 変動支給項目27
  change_pay_item_28 integer NOT NULL DEFAULT 0, -- 変動支給項目28
  change_pay_item_29 integer NOT NULL DEFAULT 0, -- 変動支給項目29
  change_pay_item_30 integer NOT NULL DEFAULT 0, -- 変動支給項目30
  tax_transport_price integer NOT NULL DEFAULT 0, -- 課税通勤費
  no_tax_transport_price integer NOT NULL DEFAULT 0, -- 非課税通勤費
  tax_transport_reverse integer NOT NULL DEFAULT 0, -- 課税通勤費戻入
  no_tax_transport_reverse integer NOT NULL DEFAULT 0, -- 非課税通勤費戻入
  transport_cash_price integer NOT NULL DEFAULT 0, -- 通勤費現金月割額
  transport_actual_price integer NOT NULL DEFAULT 0, -- 通勤費現物月割額
  total_pay_price integer NOT NULL DEFAULT 0, -- 総支給額
  no_tax_price integer NOT NULL DEFAULT 0, -- 非課税対象額
  reward_fixed_price integer NOT NULL DEFAULT 0, -- 報酬固定対象額
  reward_change_price integer NOT NULL DEFAULT 0, -- 報酬変動対象額
  employment_price integer NOT NULL DEFAULT 0, -- 雇用保険対象額
  payroll_price integer NOT NULL DEFAULT 0, -- 賃金対象額
  other_pay_price integer NOT NULL DEFAULT 0, -- その他支給額
  employment_insurance integer NOT NULL DEFAULT 0, -- 雇用保険料
  health_insurance integer NOT NULL DEFAULT 0, -- 健康保険料
  care_insurance integer NOT NULL DEFAULT 0, -- 介護保険料
  employee_price integer NOT NULL DEFAULT 0, -- 厚生年金
  fund_price integer NOT NULL DEFAULT 0, -- 厚生年金基金
  health_basic_insurance integer NOT NULL DEFAULT 0, -- 健保基本保険料
  health_specific_insurance integer NOT NULL DEFAULT 0, -- 健保特定保険料
  adjustment_employment_insurance integer NOT NULL DEFAULT 0, -- 調整雇用保険料
  adjustment_health_insurance integer NOT NULL DEFAULT 0, -- 調整健康保険料
  adjustment_care_insurance integer NOT NULL DEFAULT 0, -- 調整介護保険料
  adjustment_employee_price integer NOT NULL DEFAULT 0, -- 調整厚生年金
  adjustment_fund_price integer NOT NULL DEFAULT 0, -- 調整厚生年金基金
  tax_price integer NOT NULL DEFAULT 0, -- 課税対象額
  resident_tax integer NOT NULL DEFAULT 0, -- 住民税
  fixed_deduction_item_1 integer NOT NULL DEFAULT 0, -- 固定控除項目1
  fixed_deduction_item_2 integer NOT NULL DEFAULT 0, -- 固定控除項目2
  fixed_deduction_item_3 integer NOT NULL DEFAULT 0, -- 固定控除項目3
  fixed_deduction_item_4 integer NOT NULL DEFAULT 0, -- 固定控除項目4
  fixed_deduction_item_5 integer NOT NULL DEFAULT 0, -- 固定控除項目5
  fixed_deduction_item_6 integer NOT NULL DEFAULT 0, -- 固定控除項目6
  fixed_deduction_item_7 integer NOT NULL DEFAULT 0, -- 固定控除項目7
  fixed_deduction_item_8 integer NOT NULL DEFAULT 0, -- 固定控除項目8
  fixed_deduction_item_9 integer NOT NULL DEFAULT 0, -- 固定控除項目9
  fixed_deduction_item_10 integer NOT NULL DEFAULT 0, -- 固定控除項目10
  fixed_deduction_item_11 integer NOT NULL DEFAULT 0, -- 固定控除項目11
  fixed_deduction_item_12 integer NOT NULL DEFAULT 0, -- 固定控除項目12
  fixed_deduction_item_13 integer NOT NULL DEFAULT 0, -- 固定控除項目13
  fixed_deduction_item_14 integer NOT NULL DEFAULT 0, -- 固定控除項目14
  fixed_deduction_item_15 integer NOT NULL DEFAULT 0, -- 固定控除項目15
  fixed_deduction_item_16 integer NOT NULL DEFAULT 0, -- 固定控除項目16
  fixed_deduction_item_17 integer NOT NULL DEFAULT 0, -- 固定控除項目17
  fixed_deduction_item_18 integer NOT NULL DEFAULT 0, -- 固定控除項目18
  fixed_deduction_item_19 integer NOT NULL DEFAULT 0, -- 固定控除項目19
  fixed_deduction_item_20 integer NOT NULL DEFAULT 0, -- 固定控除項目20
  fixed_deduction_item_21 integer NOT NULL DEFAULT 0, -- 固定控除項目21
  fixed_deduction_item_22 integer NOT NULL DEFAULT 0, -- 固定控除項目22
  fixed_deduction_item_23 integer NOT NULL DEFAULT 0, -- 固定控除項目23
  fixed_deduction_item_24 integer NOT NULL DEFAULT 0, -- 固定控除項目24
  fixed_deduction_item_25 integer NOT NULL DEFAULT 0, -- 固定控除項目25
  fixed_deduction_item_26 integer NOT NULL DEFAULT 0, -- 固定控除項目26
  fixed_deduction_item_27 integer NOT NULL DEFAULT 0, -- 固定控除項目27
  fixed_deduction_item_28 integer NOT NULL DEFAULT 0, -- 固定控除項目28
  fixed_deduction_item_29 integer NOT NULL DEFAULT 0, -- 固定控除項目29
  fixed_deduction_item_30 integer NOT NULL DEFAULT 0, -- 固定控除項目30
  calc_deduction_item_1 integer NOT NULL DEFAULT 0, -- 計算控除項目1
  calc_deduction_item_2 integer NOT NULL DEFAULT 0, -- 計算控除項目2
  calc_deduction_item_3 integer NOT NULL DEFAULT 0, -- 計算控除項目3
  calc_deduction_item_4 integer NOT NULL DEFAULT 0, -- 計算控除項目4
  calc_deduction_item_5 integer NOT NULL DEFAULT 0, -- 計算控除項目5
  calc_deduction_item_6 integer NOT NULL DEFAULT 0, -- 計算控除項目6
  calc_deduction_item_7 integer NOT NULL DEFAULT 0, -- 計算控除項目7
  calc_deduction_item_8 integer NOT NULL DEFAULT 0, -- 計算控除項目8
  calc_deduction_item_9 integer NOT NULL DEFAULT 0, -- 計算控除項目9
  calc_deduction_item_10 integer NOT NULL DEFAULT 0, -- 計算控除項目10
  calc_deduction_item_11 integer NOT NULL DEFAULT 0, -- 計算控除項目11
  calc_deduction_item_12 integer NOT NULL DEFAULT 0, -- 計算控除項目12
  calc_deduction_item_13 integer NOT NULL DEFAULT 0, -- 計算控除項目13
  calc_deduction_item_14 integer NOT NULL DEFAULT 0, -- 計算控除項目14
  calc_deduction_item_15 integer NOT NULL DEFAULT 0, -- 計算控除項目15
  calc_deduction_item_16 integer NOT NULL DEFAULT 0, -- 計算控除項目16
  calc_deduction_item_17 integer NOT NULL DEFAULT 0, -- 計算控除項目17
  calc_deduction_item_18 integer NOT NULL DEFAULT 0, -- 計算控除項目18
  calc_deduction_item_19 integer NOT NULL DEFAULT 0, -- 計算控除項目19
  calc_deduction_item_20 integer NOT NULL DEFAULT 0, -- 計算控除項目20
  change_deduction_item_1 integer NOT NULL DEFAULT 0, -- 変動控除項目1
  change_deduction_item_2 integer NOT NULL DEFAULT 0, -- 変動控除項目2
  change_deduction_item_3 integer NOT NULL DEFAULT 0, -- 変動控除項目3
  change_deduction_item_4 integer NOT NULL DEFAULT 0, -- 変動控除項目4
  change_deduction_item_5 integer NOT NULL DEFAULT 0, -- 変動控除項目5
  change_deduction_item_6 integer NOT NULL DEFAULT 0, -- 変動控除項目6
  change_deduction_item_7 integer NOT NULL DEFAULT 0, -- 変動控除項目7
  change_deduction_item_8 integer NOT NULL DEFAULT 0, -- 変動控除項目8
  change_deduction_item_9 integer NOT NULL DEFAULT 0, -- 変動控除項目9
  change_deduction_item_10 integer NOT NULL DEFAULT 0, -- 変動控除項目10
  change_deduction_item_11 integer NOT NULL DEFAULT 0, -- 変動控除項目11
  change_deduction_item_12 integer NOT NULL DEFAULT 0, -- 変動控除項目12
  change_deduction_item_13 integer NOT NULL DEFAULT 0, -- 変動控除項目13
  change_deduction_item_14 integer NOT NULL DEFAULT 0, -- 変動控除項目14
  change_deduction_item_15 integer NOT NULL DEFAULT 0, -- 変動控除項目15
  change_deduction_item_16 integer NOT NULL DEFAULT 0, -- 変動控除項目16
  change_deduction_item_17 integer NOT NULL DEFAULT 0, -- 変動控除項目17
  change_deduction_item_18 integer NOT NULL DEFAULT 0, -- 変動控除項目18
  change_deduction_item_19 integer NOT NULL DEFAULT 0, -- 変動控除項目19
  change_deduction_item_20 integer NOT NULL DEFAULT 0, -- 変動控除項目20
  transport_deduction integer NOT NULL DEFAULT 0, -- 通勤費控除
  previous_brought_forward integer NOT NULL DEFAULT 0, -- 前月度繰越額
  adjustment_excessive integer NOT NULL DEFAULT 0, -- 年調過不足額
  deduction_total integer NOT NULL DEFAULT 0, -- 控除合計
  balance_pay_price integer NOT NULL DEFAULT 0, -- 差引支給額
  account_price_1 integer NOT NULL DEFAULT 0, -- 口座１振込額
  account_price_2 integer NOT NULL DEFAULT 0, -- 口座２振込額
  account_price_3 integer NOT NULL DEFAULT 0, -- 口座３振込額
  cash_pay_price integer NOT NULL DEFAULT 0, -- 現金支給額
  next_brought_forward integer NOT NULL DEFAULT 0, -- 翌月繰越額
  yearly_total_pay_price integer NOT NULL DEFAULT 0, -- 年間総支給額
  yearly_withholding_price integer NOT NULL DEFAULT 0, -- 年間源泉対象額
  yearly_no_tax_price integer NOT NULL DEFAULT 0, -- 年間非課税対象額
  yearly_social_price integer NOT NULL DEFAULT 0, -- 年間社会保険料
  yearly_income_tax_price integer NOT NULL DEFAULT 0, -- 年間所得税
  owner_employment_insurance integer NOT NULL DEFAULT 0, -- 事業主雇用保険料
  owner_accident_insurance integer NOT NULL DEFAULT 0, -- 事業主労災保険料
  owner_adjustment_employment_insurance integer NOT NULL DEFAULT 0, -- 事業主調整雇用保険料
  owner_adjustment_accident_insurance integer NOT NULL DEFAULT 0, -- 事業主調整労災保険料
  owner_health_insurance integer NOT NULL DEFAULT 0, -- 事業主健康保険料
  owner_care_insurance integer NOT NULL DEFAULT 0, -- 事業主介護保険料
  owner_employee_price integer NOT NULL DEFAULT 0, -- 事業主厚生年金
  owner_fund_price integer NOT NULL DEFAULT 0, -- 事業主厚生年金基金
  owner_child_price integer NOT NULL DEFAULT 0, -- 事業主児童手当拠出金
  owner_health_basic_insurance integer NOT NULL DEFAULT 0, -- 事業主健保基本保険料
  owner_health_specific_insurance integer NOT NULL DEFAULT 0, -- 事業主健保特定保険料
  owner_adjustment_health_insurance integer NOT NULL DEFAULT 0, -- 事業主調整健康保険料
  owner_adjustment_care_insurance integer NOT NULL DEFAULT 0, -- 事業主調整介護保険料
  owner_adjustment_employee_price integer NOT NULL DEFAULT 0, -- 事業主調整厚生年金
  owner_adjustment_fund_price integer NOT NULL DEFAULT 0, -- 事業主調整厚生年金基金
  owner_adjustment_child_price integer NOT NULL DEFAULT 0, -- 事業主調整児童手当拠出金
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_payroll_parameter_calc_pkey PRIMARY KEY (prd_payroll_parameter_calc_id )
)
;
COMMENT ON TABLE prd_payroll_parameter_calc IS '給与計算パラメータデータ';
COMMENT ON COLUMN prd_payroll_parameter_calc.prd_payroll_parameter_calc_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_payroll_parameter_calc.execute_date IS '処理年月';
COMMENT ON COLUMN prd_payroll_parameter_calc.execute_times IS '処理回数';
COMMENT ON COLUMN prd_payroll_parameter_calc.personal_id IS '個人ID';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_work IS '出勤日数';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_absence IS '欠勤日数';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_paid_holiday IS '有休取得日数';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_remainder_paid_holiday IS '有休残日数';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_base_work IS '要勤務日数';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_times_diem IS '日給者日数';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_1 IS '日数項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_2 IS '日数項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_3 IS '日数項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_4 IS '日数項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_5 IS '日数項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_6 IS '日数項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_7 IS '日数項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_8 IS '日数項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_9 IS '日数項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.day_item_10 IS '日数項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.hourly_waqe_hour IS '時給者時間数';
COMMENT ON COLUMN prd_payroll_parameter_calc.overtime_hour IS '残業時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.latetime_hour IS '深夜残業時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.legal_overtime_hour IS '法定休日残業時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.legal_latetime_hour IS '法定休日深夜残業時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.specific_overtime_hour IS '所定休日残業時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.specific_latetime_hour IS '所定休日深夜残業時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.hour_45_over_hour IS '４５時間越割増時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.hour_60_over_hour IS '６０時間越割増時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.late_early_hour IS '遅刻早退等控除時間';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_1 IS '時間項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_2 IS '時間項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_3 IS '時間項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_4 IS '時間項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_5 IS '時間項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_6 IS '時間項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_7 IS '時間項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_8 IS '時間項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_9 IS '時間項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_10 IS '時間項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_11 IS '時間項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_12 IS '時間項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_13 IS '時間項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_14 IS '時間項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.time_item_15 IS '時間項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_1 IS '回数項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_2 IS '回数項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_3 IS '回数項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_4 IS '回数項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_5 IS '回数項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_6 IS '回数項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_7 IS '回数項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_8 IS '回数項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_9 IS '回数項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_10 IS '回数項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_11 IS '回数項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_12 IS '回数項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_13 IS '回数項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_14 IS '回数項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_15 IS '回数項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_16 IS '回数項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_17 IS '回数項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_18 IS '回数項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_19 IS '回数項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.times_item_20 IS '回数項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.rate_item_1 IS '倍率項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.rate_item_2 IS '倍率項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.rate_item_3 IS '倍率項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.rate_item_4 IS '倍率項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.rate_item_5 IS '倍率項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_diem IS '日給単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_hourly IS '時給単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_overtime_basic IS '残業基準単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_overtime IS '残業単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_latetime IS '深夜残業単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_legal_overtime IS '法定休日残業単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_legal_latetime IS '法定休日深夜残業単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_specific_overtime IS '所定休日残業単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_specific_latetime IS '所定休日深夜残業単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_45_hour_over IS '４５時間越割増単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_60_hour_over IS '６０時間越割増単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_late_early_basic IS '遅早等控除基準単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_late_early IS '遅早等控除単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_absence_basic IS '欠勤控除基準単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_absence IS '欠勤控除単価';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_1 IS '単価項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_2 IS '単価項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_3 IS '単価項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_4 IS '単価項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_5 IS '単価項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_6 IS '単価項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_7 IS '単価項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_8 IS '単価項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_9 IS '単価項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_10 IS '単価項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_11 IS '単価項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_12 IS '単価項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_13 IS '単価項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_14 IS '単価項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_15 IS '単価項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_16 IS '単価項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_17 IS '単価項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_18 IS '単価項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_19 IS '単価項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.unit_item_20 IS '単価項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.payroll_monthly IS '月給者基本給';
COMMENT ON COLUMN prd_payroll_parameter_calc.payroll_diem IS '日給者基本給';
COMMENT ON COLUMN prd_payroll_parameter_calc.payroll_hourly IS '時給者基本給';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_1 IS '固定支給項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_2 IS '固定支給項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_3 IS '固定支給項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_4 IS '固定支給項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_5 IS '固定支給項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_6 IS '固定支給項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_7 IS '固定支給項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_8 IS '固定支給項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_9 IS '固定支給項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_10 IS '固定支給項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_11 IS '固定支給項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_12 IS '固定支給項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_13 IS '固定支給項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_14 IS '固定支給項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_15 IS '固定支給項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_16 IS '固定支給項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_17 IS '固定支給項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_18 IS '固定支給項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_19 IS '固定支給項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_20 IS '固定支給項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_21 IS '固定支給項目21';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_22 IS '固定支給項目22';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_23 IS '固定支給項目23';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_24 IS '固定支給項目24';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_25 IS '固定支給項目25';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_26 IS '固定支給項目26';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_27 IS '固定支給項目27';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_28 IS '固定支給項目28';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_29 IS '固定支給項目29';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_pay_item_30 IS '固定支給項目30';
COMMENT ON COLUMN prd_payroll_parameter_calc.overtime_allowance IS '普通残業手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.latetime_allowance IS '深夜残業手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.legal_overtime_allowance IS '法定休日残業手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.legal_latetime_allowance IS '法定休日深夜残業手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.specific_overtime_allowance IS '所定休日残業手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.specific_latetime_allowance IS '所定休日深夜残業手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.hour_45_over_allowance IS '４５時間越割増手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.hour_60_over_allowance IS '６０時間越割増手当';
COMMENT ON COLUMN prd_payroll_parameter_calc.late_early_price IS '遅早等控除額';
COMMENT ON COLUMN prd_payroll_parameter_calc.absence_price IS '欠勤控除額';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_1 IS '計算支給項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_2 IS '計算支給項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_3 IS '計算支給項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_4 IS '計算支給項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_5 IS '計算支給項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_6 IS '計算支給項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_7 IS '計算支給項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_8 IS '計算支給項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_9 IS '計算支給項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_10 IS '計算支給項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_11 IS '計算支給項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_12 IS '計算支給項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_13 IS '計算支給項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_14 IS '計算支給項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_15 IS '計算支給項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_16 IS '計算支給項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_17 IS '計算支給項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_18 IS '計算支給項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_19 IS '計算支給項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_pay_item_20 IS '計算支給項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_1 IS '変動支給項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_2 IS '変動支給項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_3 IS '変動支給項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_4 IS '変動支給項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_5 IS '変動支給項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_6 IS '変動支給項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_7 IS '変動支給項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_8 IS '変動支給項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_9 IS '変動支給項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_10 IS '変動支給項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_11 IS '変動支給項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_12 IS '変動支給項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_13 IS '変動支給項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_14 IS '変動支給項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_15 IS '変動支給項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_16 IS '変動支給項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_17 IS '変動支給項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_18 IS '変動支給項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_19 IS '変動支給項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_20 IS '変動支給項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_21 IS '変動支給項目21';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_22 IS '変動支給項目22';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_23 IS '変動支給項目23';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_24 IS '変動支給項目24';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_25 IS '変動支給項目25';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_26 IS '変動支給項目26';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_27 IS '変動支給項目27';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_28 IS '変動支給項目28';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_29 IS '変動支給項目29';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_pay_item_30 IS '変動支給項目30';
COMMENT ON COLUMN prd_payroll_parameter_calc.tax_transport_price IS '課税通勤費';
COMMENT ON COLUMN prd_payroll_parameter_calc.no_tax_transport_price IS '非課税通勤費';
COMMENT ON COLUMN prd_payroll_parameter_calc.tax_transport_reverse IS '課税通勤費戻入';
COMMENT ON COLUMN prd_payroll_parameter_calc.no_tax_transport_reverse IS '非課税通勤費戻入';
COMMENT ON COLUMN prd_payroll_parameter_calc.transport_cash_price IS '通勤費現金月割額';
COMMENT ON COLUMN prd_payroll_parameter_calc.transport_actual_price IS '通勤費現物月割額';
COMMENT ON COLUMN prd_payroll_parameter_calc.total_pay_price IS '総支給額';
COMMENT ON COLUMN prd_payroll_parameter_calc.no_tax_price IS '非課税対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.reward_fixed_price IS '報酬固定対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.reward_change_price IS '報酬変動対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.employment_price IS '雇用保険対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.payroll_price IS '賃金対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.other_pay_price IS 'その他支給額';
COMMENT ON COLUMN prd_payroll_parameter_calc.employment_insurance IS '雇用保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.health_insurance IS '健康保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.care_insurance IS '介護保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.employee_price IS '厚生年金';
COMMENT ON COLUMN prd_payroll_parameter_calc.fund_price IS '厚生年金基金';
COMMENT ON COLUMN prd_payroll_parameter_calc.health_basic_insurance IS '健保基本保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.health_specific_insurance IS '健保特定保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.adjustment_employment_insurance IS '調整雇用保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.adjustment_health_insurance IS '調整健康保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.adjustment_care_insurance IS '調整介護保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.adjustment_employee_price IS '調整厚生年金';
COMMENT ON COLUMN prd_payroll_parameter_calc.adjustment_fund_price IS '調整厚生年金基金';
COMMENT ON COLUMN prd_payroll_parameter_calc.tax_price IS '課税対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.resident_tax IS '住民税';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_1 IS '固定控除項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_2 IS '固定控除項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_3 IS '固定控除項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_4 IS '固定控除項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_5 IS '固定控除項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_6 IS '固定控除項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_7 IS '固定控除項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_8 IS '固定控除項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_9 IS '固定控除項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_10 IS '固定控除項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_11 IS '固定控除項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_12 IS '固定控除項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_13 IS '固定控除項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_14 IS '固定控除項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_15 IS '固定控除項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_16 IS '固定控除項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_17 IS '固定控除項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_18 IS '固定控除項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_19 IS '固定控除項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_20 IS '固定控除項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_21 IS '固定控除項目21';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_22 IS '固定控除項目22';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_23 IS '固定控除項目23';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_24 IS '固定控除項目24';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_25 IS '固定控除項目25';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_26 IS '固定控除項目26';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_27 IS '固定控除項目27';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_28 IS '固定控除項目28';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_29 IS '固定控除項目29';
COMMENT ON COLUMN prd_payroll_parameter_calc.fixed_deduction_item_30 IS '固定控除項目30';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_1 IS '計算控除項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_2 IS '計算控除項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_3 IS '計算控除項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_4 IS '計算控除項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_5 IS '計算控除項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_6 IS '計算控除項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_7 IS '計算控除項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_8 IS '計算控除項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_9 IS '計算控除項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_10 IS '計算控除項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_11 IS '計算控除項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_12 IS '計算控除項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_13 IS '計算控除項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_14 IS '計算控除項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_15 IS '計算控除項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_16 IS '計算控除項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_17 IS '計算控除項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_18 IS '計算控除項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_19 IS '計算控除項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.calc_deduction_item_20 IS '計算控除項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_1 IS '変動控除項目1';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_2 IS '変動控除項目2';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_3 IS '変動控除項目3';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_4 IS '変動控除項目4';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_5 IS '変動控除項目5';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_6 IS '変動控除項目6';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_7 IS '変動控除項目7';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_8 IS '変動控除項目8';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_9 IS '変動控除項目9';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_10 IS '変動控除項目10';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_11 IS '変動控除項目11';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_12 IS '変動控除項目12';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_13 IS '変動控除項目13';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_14 IS '変動控除項目14';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_15 IS '変動控除項目15';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_16 IS '変動控除項目16';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_17 IS '変動控除項目17';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_18 IS '変動控除項目18';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_19 IS '変動控除項目19';
COMMENT ON COLUMN prd_payroll_parameter_calc.change_deduction_item_20 IS '変動控除項目20';
COMMENT ON COLUMN prd_payroll_parameter_calc.transport_deduction IS '通勤費控除';
COMMENT ON COLUMN prd_payroll_parameter_calc.previous_brought_forward IS '前月度繰越額';
COMMENT ON COLUMN prd_payroll_parameter_calc.adjustment_excessive IS '年調過不足額';
COMMENT ON COLUMN prd_payroll_parameter_calc.deduction_total IS '控除合計';
COMMENT ON COLUMN prd_payroll_parameter_calc.balance_pay_price IS '差引支給額';
COMMENT ON COLUMN prd_payroll_parameter_calc.account_price_1 IS '口座１振込額';
COMMENT ON COLUMN prd_payroll_parameter_calc.account_price_2 IS '口座２振込額';
COMMENT ON COLUMN prd_payroll_parameter_calc.account_price_3 IS '口座３振込額';
COMMENT ON COLUMN prd_payroll_parameter_calc.cash_pay_price IS '現金支給額';
COMMENT ON COLUMN prd_payroll_parameter_calc.next_brought_forward IS '翌月繰越額';
COMMENT ON COLUMN prd_payroll_parameter_calc.yearly_total_pay_price IS '年間総支給額';
COMMENT ON COLUMN prd_payroll_parameter_calc.yearly_withholding_price IS '年間源泉対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.yearly_no_tax_price IS '年間非課税対象額';
COMMENT ON COLUMN prd_payroll_parameter_calc.yearly_social_price IS '年間社会保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.yearly_income_tax_price IS '年間所得税';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_employment_insurance IS '事業主雇用保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_accident_insurance IS '事業主労災保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_adjustment_employment_insurance IS '事業主調整雇用保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_adjustment_accident_insurance IS '事業主調整労災保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_health_insurance IS '事業主健康保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_care_insurance IS '事業主介護保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_employee_price IS '事業主厚生年金';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_fund_price IS '事業主厚生年金基金';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_child_price IS '事業主児童手当拠出金';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_health_basic_insurance IS '事業主健保基本保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_health_specific_insurance IS '事業主健保特定保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_adjustment_health_insurance IS '事業主調整健康保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_adjustment_care_insurance IS '事業主調整介護保険料';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_adjustment_employee_price IS '事業主調整厚生年金';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_adjustment_fund_price IS '事業主調整厚生年金基金';
COMMENT ON COLUMN prd_payroll_parameter_calc.owner_adjustment_child_price IS '事業主調整児童手当拠出金';
COMMENT ON COLUMN prd_payroll_parameter_calc.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_payroll_parameter_calc.insert_date IS '登録日';
COMMENT ON COLUMN prd_payroll_parameter_calc.insert_user IS '登録者';
COMMENT ON COLUMN prd_payroll_parameter_calc.update_date IS '更新日';
COMMENT ON COLUMN prd_payroll_parameter_calc.update_user IS '更新者';


CREATE TABLE prd_resident_tax_transfer
(
  prd_resident_tax_transfer_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  local_public_code character varying(6) NOT NULL DEFAULT ''::character varying, -- 地方公共団体コード
  city_name character varying(32) NOT NULL DEFAULT ''::character varying, -- 市区町村名
  city_kana character varying(15) NOT NULL DEFAULT ''::character varying, -- 市区町村名カナ
  specific_number character varying(15) NOT NULL DEFAULT ''::character varying, -- 指定番号
  change_code character varying(1) NOT NULL DEFAULT ''::character varying, -- 異動コード
  number_payroll integer NOT NULL DEFAULT 0, -- 給与件数
  payroll_tax_price integer NOT NULL DEFAULT 0, -- 給与税額
  number_retirement_pay integer NOT NULL DEFAULT 0, -- 退職金件数
  retirement_pay_tax_price integer NOT NULL DEFAULT 0, -- 退職金税額
  number_total integer NOT NULL DEFAULT 0, -- 合計件数
  total_tax_price integer NOT NULL DEFAULT 0, -- 合計税額
  retirement_pay_employee integer NOT NULL DEFAULT 0, -- 退職金人員
  retirement_pay_price integer NOT NULL DEFAULT 0, -- 退職金支払額
  city_tax_price integer NOT NULL DEFAULT 0, -- 市区町村民税額
  state_tax_price integer NOT NULL DEFAULT 0, -- 都道府県民税額
  giro_account_number character varying(8) NOT NULL DEFAULT ''::character varying, -- 郵便振替口座番号
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prd_resident_tax_transfer_pkey PRIMARY KEY (prd_resident_tax_transfer_id )
)
;
COMMENT ON TABLE prd_resident_tax_transfer IS '住民税振込基礎データ';
COMMENT ON COLUMN prd_resident_tax_transfer.prd_resident_tax_transfer_id IS 'レコード識別ID';
COMMENT ON COLUMN prd_resident_tax_transfer.company_code IS '会社コード';
COMMENT ON COLUMN prd_resident_tax_transfer.local_public_code IS '地方公共団体コード';
COMMENT ON COLUMN prd_resident_tax_transfer.city_name IS '市区町村名';
COMMENT ON COLUMN prd_resident_tax_transfer.city_kana IS '市区町村名カナ';
COMMENT ON COLUMN prd_resident_tax_transfer.specific_number IS '指定番号';
COMMENT ON COLUMN prd_resident_tax_transfer.change_code IS '異動コード';
COMMENT ON COLUMN prd_resident_tax_transfer.number_payroll IS '給与件数';
COMMENT ON COLUMN prd_resident_tax_transfer.payroll_tax_price IS '給与税額';
COMMENT ON COLUMN prd_resident_tax_transfer.number_retirement_pay IS '退職金件数';
COMMENT ON COLUMN prd_resident_tax_transfer.retirement_pay_tax_price IS '退職金税額';
COMMENT ON COLUMN prd_resident_tax_transfer.number_total IS '合計件数';
COMMENT ON COLUMN prd_resident_tax_transfer.total_tax_price IS '合計税額';
COMMENT ON COLUMN prd_resident_tax_transfer.retirement_pay_employee IS '退職金人員';
COMMENT ON COLUMN prd_resident_tax_transfer.retirement_pay_price IS '退職金支払額';
COMMENT ON COLUMN prd_resident_tax_transfer.city_tax_price IS '市区町村民税額';
COMMENT ON COLUMN prd_resident_tax_transfer.state_tax_price IS '都道府県民税額';
COMMENT ON COLUMN prd_resident_tax_transfer.giro_account_number IS '郵便振替口座番号';
COMMENT ON COLUMN prd_resident_tax_transfer.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prd_resident_tax_transfer.insert_date IS '登録日';
COMMENT ON COLUMN prd_resident_tax_transfer.insert_user IS '登録者';
COMMENT ON COLUMN prd_resident_tax_transfer.update_date IS '更新日';
COMMENT ON COLUMN prd_resident_tax_transfer.update_user IS '更新者';


CREATE TABLE prm_adjustment_income_deduction
(
  prm_adjustment_income_deduction_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  above integer NOT NULL DEFAULT 0, -- 以上
  rate integer NOT NULL DEFAULT 0, -- 掛率
  deduction_price integer NOT NULL DEFAULT 0, -- 控除額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_adjustment_income_deduction_pkey PRIMARY KEY (prm_adjustment_income_deduction_id )
)
;
COMMENT ON TABLE prm_adjustment_income_deduction IS '年調所得控除後額マスタ';
COMMENT ON COLUMN prm_adjustment_income_deduction.prm_adjustment_income_deduction_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_adjustment_income_deduction.above IS '以上';
COMMENT ON COLUMN prm_adjustment_income_deduction.rate IS '掛率';
COMMENT ON COLUMN prm_adjustment_income_deduction.deduction_price IS '控除額';
COMMENT ON COLUMN prm_adjustment_income_deduction.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_adjustment_income_deduction.insert_date IS '登録日';
COMMENT ON COLUMN prm_adjustment_income_deduction.insert_user IS '登録者';
COMMENT ON COLUMN prm_adjustment_income_deduction.update_date IS '更新日';
COMMENT ON COLUMN prm_adjustment_income_deduction.update_user IS '更新者';


CREATE TABLE prm_adjustment_income_difference
(
  prm_adjustment_income_difference_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  above integer NOT NULL DEFAULT 0, -- 以上
  dire integer NOT NULL DEFAULT 0, -- 階差
  min_value integer NOT NULL DEFAULT 0, -- 最小値
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_adjustment_income_difference_pkey PRIMARY KEY (prm_adjustment_income_difference_id )
)
;
COMMENT ON TABLE prm_adjustment_income_difference IS '年調所得階差マスタ';
COMMENT ON COLUMN prm_adjustment_income_difference.prm_adjustment_income_difference_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_adjustment_income_difference.above IS '以上';
COMMENT ON COLUMN prm_adjustment_income_difference.dire IS '階差';
COMMENT ON COLUMN prm_adjustment_income_difference.min_value IS '最小値';
COMMENT ON COLUMN prm_adjustment_income_difference.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_adjustment_income_difference.insert_date IS '登録日';
COMMENT ON COLUMN prm_adjustment_income_difference.insert_user IS '登録者';
COMMENT ON COLUMN prm_adjustment_income_difference.update_date IS '更新日';
COMMENT ON COLUMN prm_adjustment_income_difference.update_user IS '更新者';


CREATE TABLE prm_adjustment_insurance_deduction
(
  prm_adjustment_insurance_deduction_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  insurance_type character varying(3) NOT NULL DEFAULT ''::character varying, -- 保険料種別
  above integer NOT NULL DEFAULT 0, -- 以上
  rate_numerator integer NOT NULL DEFAULT 0, -- 掛率分子
  rate_denominator integer NOT NULL DEFAULT 0, -- 掛率分母
  add_price integer NOT NULL DEFAULT 0, -- 加算額
  limit_deduction integer NOT NULL DEFAULT 0, -- 控除上限
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_adjustment_insurance_deduction_pkey PRIMARY KEY (prm_adjustment_insurance_deduction_id )
)
;
COMMENT ON TABLE prm_adjustment_insurance_deduction IS '年調保険料控除マスタ';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.prm_adjustment_insurance_deduction_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.insurance_type IS '保険料種別';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.above IS '以上';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.rate_numerator IS '掛率分子';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.rate_denominator IS '掛率分母';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.add_price IS '加算額';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.limit_deduction IS '控除上限';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.insert_date IS '登録日';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.insert_user IS '登録者';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.update_date IS '更新日';
COMMENT ON COLUMN prm_adjustment_insurance_deduction.update_user IS '更新者';


CREATE TABLE prm_adjustment_request
(
  prm_adjustment_request_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  adjustment_total_income integer NOT NULL DEFAULT 0, -- 年調対象合計所得
  adjustment_other_income integer NOT NULL DEFAULT 0, -- 年調対象給与外所得
  widow_total_income integer NOT NULL DEFAULT 0, -- 寡夫条件合計所得
  widower_total_income integer NOT NULL DEFAULT 0, -- 寡婦条件合計所得
  special_widow_total_income integer NOT NULL DEFAULT 0, -- 特別寡婦条件合計所得
  labor_student_total_income integer NOT NULL DEFAULT 0, -- 勤労学生条件合計所得
  labor_student_other_income integer NOT NULL DEFAULT 0, -- 勤労学生条件給与外所得
  special_terms_total_income integer NOT NULL DEFAULT 0, -- 配特条件合計所得
  basic_dependent_deduction integer NOT NULL DEFAULT 0, -- 基礎扶養控除額
  together_special_desability_deduction integer NOT NULL DEFAULT 0, -- 同居特障控除額
  special_disability_deduction integer NOT NULL DEFAULT 0, -- 特別障害控除額
  desability_deduction integer NOT NULL DEFAULT 0, -- 一般障害控除額
  special_widow_deduction integer NOT NULL DEFAULT 0, -- 特別寡婦控除額
  widow_deduction integer NOT NULL DEFAULT 0, -- 寡婦控除額
  widower_deduction integer NOT NULL DEFAULT 0, -- 寡夫控除額
  labor_student_deduction integer NOT NULL DEFAULT 0, -- 勤労学生控除額
  together_age_deduction integer NOT NULL DEFAULT 0, -- 同居老親控除額
  specific_dependent_deduction integer NOT NULL DEFAULT 0, -- 特定扶養控除額
  age_dependent_deduction integer NOT NULL DEFAULT 0, -- 老人扶養控除額
  age_spouse_deduction integer NOT NULL DEFAULT 0, -- 老人配偶者控除額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_adjustment_request_pkey PRIMARY KEY (prm_adjustment_request_id )
)
;
COMMENT ON TABLE prm_adjustment_request IS '年調要件マスタ';
COMMENT ON COLUMN prm_adjustment_request.prm_adjustment_request_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_adjustment_request.adjustment_total_income IS '年調対象合計所得';
COMMENT ON COLUMN prm_adjustment_request.adjustment_other_income IS '年調対象給与外所得';
COMMENT ON COLUMN prm_adjustment_request.widow_total_income IS '寡夫条件合計所得';
COMMENT ON COLUMN prm_adjustment_request.widower_total_income IS '寡婦条件合計所得';
COMMENT ON COLUMN prm_adjustment_request.special_widow_total_income IS '特別寡婦条件合計所得';
COMMENT ON COLUMN prm_adjustment_request.labor_student_total_income IS '勤労学生条件合計所得';
COMMENT ON COLUMN prm_adjustment_request.labor_student_other_income IS '勤労学生条件給与外所得';
COMMENT ON COLUMN prm_adjustment_request.special_terms_total_income IS '配特条件合計所得';
COMMENT ON COLUMN prm_adjustment_request.basic_dependent_deduction IS '基礎扶養控除額';
COMMENT ON COLUMN prm_adjustment_request.together_special_desability_deduction IS '同居特障控除額';
COMMENT ON COLUMN prm_adjustment_request.special_disability_deduction IS '特別障害控除額';
COMMENT ON COLUMN prm_adjustment_request.desability_deduction IS '一般障害控除額';
COMMENT ON COLUMN prm_adjustment_request.special_widow_deduction IS '特別寡婦控除額';
COMMENT ON COLUMN prm_adjustment_request.widow_deduction IS '寡婦控除額';
COMMENT ON COLUMN prm_adjustment_request.widower_deduction IS '寡夫控除額';
COMMENT ON COLUMN prm_adjustment_request.labor_student_deduction IS '勤労学生控除額';
COMMENT ON COLUMN prm_adjustment_request.together_age_deduction IS '同居老親控除額';
COMMENT ON COLUMN prm_adjustment_request.specific_dependent_deduction IS '特定扶養控除額';
COMMENT ON COLUMN prm_adjustment_request.age_dependent_deduction IS '老人扶養控除額';
COMMENT ON COLUMN prm_adjustment_request.age_spouse_deduction IS '老人配偶者控除額';
COMMENT ON COLUMN prm_adjustment_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_adjustment_request.insert_date IS '登録日';
COMMENT ON COLUMN prm_adjustment_request.insert_user IS '登録者';
COMMENT ON COLUMN prm_adjustment_request.update_date IS '更新日';
COMMENT ON COLUMN prm_adjustment_request.update_user IS '更新者';


CREATE TABLE prm_adjustment_tax
(
  prm_adjustment_tax_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  above integer NOT NULL DEFAULT 0, -- 以上
  tax_rate integer NOT NULL DEFAULT 0, -- 税率
  deduction_price integer NOT NULL DEFAULT 0, -- 控除額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_adjustment_tax_pkey PRIMARY KEY (prm_adjustment_tax_id )
)
;
COMMENT ON TABLE prm_adjustment_tax IS '年調年税額マスタ';
COMMENT ON COLUMN prm_adjustment_tax.prm_adjustment_tax_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_adjustment_tax.above IS '以上';
COMMENT ON COLUMN prm_adjustment_tax.tax_rate IS '税率';
COMMENT ON COLUMN prm_adjustment_tax.deduction_price IS '控除額';
COMMENT ON COLUMN prm_adjustment_tax.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_adjustment_tax.insert_date IS '登録日';
COMMENT ON COLUMN prm_adjustment_tax.insert_user IS '登録者';
COMMENT ON COLUMN prm_adjustment_tax.update_date IS '更新日';
COMMENT ON COLUMN prm_adjustment_tax.update_user IS '更新者';


CREATE TABLE prm_bank
(
  prm_bank_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  bank_code character varying(4) NOT NULL DEFAULT ''::character varying, -- 銀行コード
  branch_code character varying(3) NOT NULL DEFAULT ''::character varying, -- 支店コード
  bank_name character varying(20) NOT NULL DEFAULT ''::character varying, -- 銀行名漢字
  bank_kana character varying(15) NOT NULL DEFAULT ''::character varying, -- 銀行名ｶﾅ
  branch_name character varying(20) NOT NULL DEFAULT ''::character varying, -- 支店名漢字
  branch_kana character varying(15) NOT NULL DEFAULT ''::character varying, -- 支店名ｶﾅ
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_bank_pkey PRIMARY KEY (prm_bank_id )
)
;
COMMENT ON TABLE prm_bank IS '銀行マスタ';
COMMENT ON COLUMN prm_bank.prm_bank_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_bank.bank_code IS '銀行コード';
COMMENT ON COLUMN prm_bank.branch_code IS '支店コード';
COMMENT ON COLUMN prm_bank.bank_name IS '銀行名漢字';
COMMENT ON COLUMN prm_bank.bank_kana IS '銀行名ｶﾅ';
COMMENT ON COLUMN prm_bank.branch_name IS '支店名漢字';
COMMENT ON COLUMN prm_bank.branch_kana IS '支店名ｶﾅ';
COMMENT ON COLUMN prm_bank.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_bank.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_bank.insert_date IS '登録日';
COMMENT ON COLUMN prm_bank.insert_user IS '登録者';
COMMENT ON COLUMN prm_bank.update_date IS '更新日';
COMMENT ON COLUMN prm_bank.update_user IS '更新者';


CREATE TABLE prm_bonus_k_tax
(
  prm_bonus_k_tax_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 対象年月
  depnendent_total integer NOT NULL DEFAULT 0, -- 扶養親族等人数
  above integer NOT NULL DEFAULT 0, -- 以上
  tax_rate double precision NOT NULL DEFAULT 0, -- 税率
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_bonus_k_tax_pkey PRIMARY KEY (prm_bonus_k_tax_id )
)
;
COMMENT ON TABLE prm_bonus_k_tax IS '賞与甲欄税額マスタ';
COMMENT ON COLUMN prm_bonus_k_tax.prm_bonus_k_tax_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_bonus_k_tax.execute_date IS '対象年月';
COMMENT ON COLUMN prm_bonus_k_tax.depnendent_total IS '扶養親族等人数';
COMMENT ON COLUMN prm_bonus_k_tax.above IS '以上';
COMMENT ON COLUMN prm_bonus_k_tax.tax_rate IS '税率';
COMMENT ON COLUMN prm_bonus_k_tax.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_bonus_k_tax.insert_date IS '登録日';
COMMENT ON COLUMN prm_bonus_k_tax.insert_user IS '登録者';
COMMENT ON COLUMN prm_bonus_k_tax.update_date IS '更新日';
COMMENT ON COLUMN prm_bonus_k_tax.update_user IS '更新者';


CREATE TABLE prm_bonus_o_tax
(
  prm_bonus_o_tax_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 対象年月
  above integer NOT NULL DEFAULT 0, -- 以上
  tax_rate double precision NOT NULL DEFAULT 0, -- 税率
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_bonus_o_tax_pkey PRIMARY KEY (prm_bonus_o_tax_id )
)
;
COMMENT ON TABLE prm_bonus_o_tax IS '賞与乙欄税額マスタ';
COMMENT ON COLUMN prm_bonus_o_tax.prm_bonus_o_tax_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_bonus_o_tax.execute_date IS '対象年月';
COMMENT ON COLUMN prm_bonus_o_tax.above IS '以上';
COMMENT ON COLUMN prm_bonus_o_tax.tax_rate IS '税率';
COMMENT ON COLUMN prm_bonus_o_tax.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_bonus_o_tax.insert_date IS '登録日';
COMMENT ON COLUMN prm_bonus_o_tax.insert_user IS '登録者';
COMMENT ON COLUMN prm_bonus_o_tax.update_date IS '更新日';
COMMENT ON COLUMN prm_bonus_o_tax.update_user IS '更新者';


CREATE TABLE prm_company
(
  prm_company_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  activate_date date NOT NULL, -- 有効日
  company_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 会社名
  represent_full_name character varying(100) NOT NULL DEFAULT ''::character varying, -- 代表者氏名
  represent_title character varying(50) NOT NULL DEFAULT 0, -- 代表者肩書
  corporate_number character varying(26) NOT NULL DEFAULT ''::character varying, -- 法人番号
  postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying, -- 郵便番号1
  postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 郵便番号2
  prefecture character varying(10) NOT NULL DEFAULT ''::character varying, -- 都道府県
  address_1 character varying(50) NOT NULL DEFAULT ''::character varying, -- 市区町村
  address_2 character varying(50) NOT NULL DEFAULT ''::character varying, -- 番地
  address_3 character varying(50) NOT NULL DEFAULT ''::character varying, -- 建物情報
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying, -- 電話番号1
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号2
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号3
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_company_pkey PRIMARY KEY (prm_company_id )
)
;
COMMENT ON TABLE prm_company IS '会社マスタ';
COMMENT ON COLUMN prm_company.prm_company_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_company.company_code IS '会社コード';
COMMENT ON COLUMN prm_company.activate_date IS '有効日';
COMMENT ON COLUMN prm_company.company_name IS '会社名';
COMMENT ON COLUMN prm_company.represent_full_name IS '代表者氏名';
COMMENT ON COLUMN prm_company.represent_title IS '代表者肩書';
COMMENT ON COLUMN prm_company.corporate_number IS '法人番号';
COMMENT ON COLUMN prm_company.postal_code_1 IS '郵便番号1';
COMMENT ON COLUMN prm_company.postal_code_2 IS '郵便番号2';
COMMENT ON COLUMN prm_company.prefecture IS '都道府県';
COMMENT ON COLUMN prm_company.address_1 IS '市区町村';
COMMENT ON COLUMN prm_company.address_2 IS '番地';
COMMENT ON COLUMN prm_company.address_3 IS '建物情報';
COMMENT ON COLUMN prm_company.phone_number_1 IS '電話番号1';
COMMENT ON COLUMN prm_company.phone_number_2 IS '電話番号2';
COMMENT ON COLUMN prm_company.phone_number_3 IS '電話番号3';
COMMENT ON COLUMN prm_company.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_company.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_company.insert_date IS '登録日';
COMMENT ON COLUMN prm_company.insert_user IS '登録者';
COMMENT ON COLUMN prm_company.update_date IS '更新日';
COMMENT ON COLUMN prm_company.update_user IS '更新者';


CREATE TABLE prm_company_payroll
(
  prm_company_payroll_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  activate_date date NOT NULL, -- 有効日
  start_date date, -- 年度起算日
  fund_type integer NOT NULL DEFAULT 0, -- 厚年基金区分
  bill_use_type integer NOT NULL DEFAULT 0, -- 二千円札使用区分
  cash_fraction_unit integer NOT NULL DEFAULT 0, -- 現金端数繰越単位
  health_collection_type integer NOT NULL DEFAULT 0, -- 健保厚年徴収区分
  resident_tax_type integer NOT NULL DEFAULT 0, -- 住民税徴収区分
  resident_exchange_code character varying(2) NOT NULL DEFAULT '99'::character varying, -- 住民税仕向コード
  resident_tax_format_type integer NOT NULL DEFAULT 0, -- 住民税フォーマット区分
  resident_move_default character varying(1) NOT NULL DEFAULT ' '::character varying, -- 住民税異動初期値
  resident_payment_limit date, -- 住民税納付限
  resident_payment_month date, -- 住民税納付月分
  constant_name_1 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称1
  constant_value_1 double precision NOT NULL DEFAULT 0, -- 共通定数内容1
  constant_name_2 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称2
  constant_value_2 double precision NOT NULL DEFAULT 0, -- 共通定数内容2
  constant_name_3 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称3
  constant_value_3 double precision NOT NULL DEFAULT 0, -- 共通定数内容3
  constant_name_4 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称4
  constant_value_4 double precision NOT NULL DEFAULT 0, -- 共通定数内容4
  constant_name_5 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称5
  constant_value_5 double precision NOT NULL DEFAULT 0, -- 共通定数内容5
  constant_name_6 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称6
  constant_value_6 double precision NOT NULL DEFAULT 0, -- 共通定数内容6
  constant_name_7 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称7
  constant_value_7 double precision NOT NULL DEFAULT 0, -- 共通定数内容7
  constant_name_8 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称8
  constant_value_8 double precision NOT NULL DEFAULT 0, -- 共通定数内容8
  constant_name_9 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称9
  constant_value_9 double precision NOT NULL DEFAULT 0, -- 共通定数内容9
  constant_name_10 character varying(10) NOT NULL DEFAULT ''::character varying, -- 共通定数名称10
  constant_value_10 double precision NOT NULL DEFAULT 0, -- 共通定数内容10
  payroll_execute_date date NOT NULL, -- 給与処理年月
  payroll_execute_times integer NOT NULL DEFAULT 1, -- 給与処理回数
  payroll_basic_date date NOT NULL, -- 給与処理基準日
  payroll_target_period_from date NOT NULL, -- 給与対象期間自
  payroll_target_period_to date NOT NULL, -- 給与対象期間至
  bonus_execute_date date NOT NULL, -- 賞与処理年月
  bonus_execute_times integer NOT NULL DEFAULT 1, -- 賞与処理回数
  bonus_basic_date date NOT NULL, -- 賞与処理基準日
  bonus_period integer NOT NULL DEFAULT 1, -- 賞与対象期間
  bonus_target_period_from date NOT NULL, -- 賞与対象期間自
  bonus_target_period_to date NOT NULL, -- 賞与対象期間至
  payroll_bonus_last_execute integer NOT NULL, -- 給与賞与最終更新年
  adjustment_fiscal integer NOT NULL, -- 年末調整処理年度
  adjustment_calc_type integer NOT NULL DEFAULT 1, -- 年調計算対象
  adjustment_employee_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 年調計算社員コード
  payroll_period_from date NOT NULL, -- 年調給与期間自
  payroll_times_from integer NOT NULL DEFAULT 1, -- 年調給与回数自
  payroll_period_to date NOT NULL, -- 年調給与期間至
  payroll_times_to integer NOT NULL DEFAULT 1, -- 年調給与回数至
  bonus_period_from date NOT NULL, -- 年調賞与期間自
  bonus_times_from integer NOT NULL DEFAULT 1, -- 年調賞与回数自
  bonus_period_to date NOT NULL, -- 年調賞与期間至
  bonus_times_to integer NOT NULL DEFAULT 1, -- 年調賞与回数至
  adjustment_type integer NOT NULL DEFAULT 0, -- 年末調整処理区分
  payroll_change_state integer NOT NULL DEFAULT 0, -- 月変算定処理状態
  payroll_change_start date NOT NULL, -- 月変算定期間自
  payroll_change_end date NOT NULL, -- 月変算定期間至
  payroll_change_basic date NOT NULL, -- 月変算定基準日
  payroll_change_type integer NOT NULL DEFAULT 1, -- 月変処理区分
  change_revision_fiscal date NOT NULL, -- 月変改定年月度
  change_minus_fiscal date NOT NULL, -- 月変引去年月度
  calc_execute_type integer NOT NULL DEFAULT 1, -- 算定処理区分
  calc_revision_fiscal date NOT NULL, -- 算定改定年月度
  calc_minus_fiscal date NOT NULL, -- 算定引去年月度
  health_lower_under integer NOT NULL DEFAULT 0, -- 健保報酬下限未満
  health_upper_over integer NOT NULL DEFAULT 0, -- 健保報酬上限以上
  employee_lower_under integer NOT NULL DEFAULT 0, -- 厚年報酬下限未満
  employee_upper_over integer NOT NULL DEFAULT 0, -- 厚年報酬上限以上
  data_save_limit integer NOT NULL DEFAULT 3, -- レコード保存年限
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_company_payroll_pkey PRIMARY KEY (prm_company_payroll_id )
)
;
COMMENT ON TABLE prm_company_payroll IS '会社給与マスタ';
COMMENT ON COLUMN prm_company_payroll.prm_company_payroll_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_company_payroll.company_code IS '会社コード';
COMMENT ON COLUMN prm_company_payroll.activate_date IS '有効日';
COMMENT ON COLUMN prm_company_payroll.start_date IS '年度起算日';
COMMENT ON COLUMN prm_company_payroll.fund_type IS '厚年基金区分';
COMMENT ON COLUMN prm_company_payroll.bill_use_type IS '二千円札使用区分';
COMMENT ON COLUMN prm_company_payroll.cash_fraction_unit IS '現金端数繰越単位';
COMMENT ON COLUMN prm_company_payroll.health_collection_type IS '健保厚年徴収区分';
COMMENT ON COLUMN prm_company_payroll.resident_tax_type IS '住民税徴収区分';
COMMENT ON COLUMN prm_company_payroll.resident_exchange_code IS '住民税仕向コード';
COMMENT ON COLUMN prm_company_payroll.resident_tax_format_type IS '住民税フォーマット区分';
COMMENT ON COLUMN prm_company_payroll.resident_move_default IS '住民税異動初期値';
COMMENT ON COLUMN prm_company_payroll.resident_payment_limit IS '住民税納付限';
COMMENT ON COLUMN prm_company_payroll.resident_payment_month IS '住民税納付月分';
COMMENT ON COLUMN prm_company_payroll.constant_name_1 IS '共通定数名称1';
COMMENT ON COLUMN prm_company_payroll.constant_value_1 IS '共通定数内容1';
COMMENT ON COLUMN prm_company_payroll.constant_name_2 IS '共通定数名称2';
COMMENT ON COLUMN prm_company_payroll.constant_value_2 IS '共通定数内容2';
COMMENT ON COLUMN prm_company_payroll.constant_name_3 IS '共通定数名称3';
COMMENT ON COLUMN prm_company_payroll.constant_value_3 IS '共通定数内容3';
COMMENT ON COLUMN prm_company_payroll.constant_name_4 IS '共通定数名称4';
COMMENT ON COLUMN prm_company_payroll.constant_value_4 IS '共通定数内容4';
COMMENT ON COLUMN prm_company_payroll.constant_name_5 IS '共通定数名称5';
COMMENT ON COLUMN prm_company_payroll.constant_value_5 IS '共通定数内容5';
COMMENT ON COLUMN prm_company_payroll.constant_name_6 IS '共通定数名称6';
COMMENT ON COLUMN prm_company_payroll.constant_value_6 IS '共通定数内容6';
COMMENT ON COLUMN prm_company_payroll.constant_name_7 IS '共通定数名称7';
COMMENT ON COLUMN prm_company_payroll.constant_value_7 IS '共通定数内容7';
COMMENT ON COLUMN prm_company_payroll.constant_name_8 IS '共通定数名称8';
COMMENT ON COLUMN prm_company_payroll.constant_value_8 IS '共通定数内容8';
COMMENT ON COLUMN prm_company_payroll.constant_name_9 IS '共通定数名称9';
COMMENT ON COLUMN prm_company_payroll.constant_value_9 IS '共通定数内容9';
COMMENT ON COLUMN prm_company_payroll.constant_name_10 IS '共通定数名称10';
COMMENT ON COLUMN prm_company_payroll.constant_value_10 IS '共通定数内容10';
COMMENT ON COLUMN prm_company_payroll.payroll_execute_date IS '給与処理年月';
COMMENT ON COLUMN prm_company_payroll.payroll_execute_times IS '給与処理回数';
COMMENT ON COLUMN prm_company_payroll.payroll_basic_date IS '給与処理基準日';
COMMENT ON COLUMN prm_company_payroll.payroll_target_period_from IS '給与対象期間自';
COMMENT ON COLUMN prm_company_payroll.payroll_target_period_to IS '給与対象期間至';
COMMENT ON COLUMN prm_company_payroll.bonus_execute_date IS '賞与処理年月';
COMMENT ON COLUMN prm_company_payroll.bonus_execute_times IS '賞与処理回数';
COMMENT ON COLUMN prm_company_payroll.bonus_basic_date IS '賞与処理基準日';
COMMENT ON COLUMN prm_company_payroll.bonus_period IS '賞与対象期間';
COMMENT ON COLUMN prm_company_payroll.bonus_target_period_from IS '賞与対象期間自';
COMMENT ON COLUMN prm_company_payroll.bonus_target_period_to IS '賞与対象期間至';
COMMENT ON COLUMN prm_company_payroll.payroll_bonus_last_execute IS '給与賞与最終更新年';
COMMENT ON COLUMN prm_company_payroll.adjustment_fiscal IS '年末調整処理年度';
COMMENT ON COLUMN prm_company_payroll.adjustment_calc_type IS '年調計算対象';
COMMENT ON COLUMN prm_company_payroll.adjustment_employee_code IS '年調計算社員コード';
COMMENT ON COLUMN prm_company_payroll.payroll_period_from IS '年調給与期間自';
COMMENT ON COLUMN prm_company_payroll.payroll_times_from IS '年調給与回数自';
COMMENT ON COLUMN prm_company_payroll.payroll_period_to IS '年調給与期間至';
COMMENT ON COLUMN prm_company_payroll.payroll_times_to IS '年調給与回数至';
COMMENT ON COLUMN prm_company_payroll.bonus_period_from IS '年調賞与期間自';
COMMENT ON COLUMN prm_company_payroll.bonus_times_from IS '年調賞与回数自';
COMMENT ON COLUMN prm_company_payroll.bonus_period_to IS '年調賞与期間至';
COMMENT ON COLUMN prm_company_payroll.bonus_times_to IS '年調賞与回数至';
COMMENT ON COLUMN prm_company_payroll.adjustment_type IS '年末調整処理区分';
COMMENT ON COLUMN prm_company_payroll.payroll_change_state IS '月変算定処理状態';
COMMENT ON COLUMN prm_company_payroll.payroll_change_start IS '月変算定期間自';
COMMENT ON COLUMN prm_company_payroll.payroll_change_end IS '月変算定期間至';
COMMENT ON COLUMN prm_company_payroll.payroll_change_basic IS '月変算定基準日';
COMMENT ON COLUMN prm_company_payroll.payroll_change_type IS '月変処理区分';
COMMENT ON COLUMN prm_company_payroll.change_revision_fiscal IS '月変改定年月度';
COMMENT ON COLUMN prm_company_payroll.change_minus_fiscal IS '月変引去年月度';
COMMENT ON COLUMN prm_company_payroll.calc_execute_type IS '算定処理区分';
COMMENT ON COLUMN prm_company_payroll.calc_revision_fiscal IS '算定改定年月度';
COMMENT ON COLUMN prm_company_payroll.calc_minus_fiscal IS '算定引去年月度';
COMMENT ON COLUMN prm_company_payroll.health_lower_under IS '健保報酬下限未満';
COMMENT ON COLUMN prm_company_payroll.health_upper_over IS '健保報酬上限以上';
COMMENT ON COLUMN prm_company_payroll.employee_lower_under IS '厚年報酬下限未満';
COMMENT ON COLUMN prm_company_payroll.employee_upper_over IS '厚年報酬上限以上';
COMMENT ON COLUMN prm_company_payroll.data_save_limit IS 'レコード保存年限';
COMMENT ON COLUMN prm_company_payroll.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_company_payroll.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_company_payroll.insert_date IS '登録日';
COMMENT ON COLUMN prm_company_payroll.insert_user IS '登録者';
COMMENT ON COLUMN prm_company_payroll.update_date IS '更新日';
COMMENT ON COLUMN prm_company_payroll.update_user IS '更新者';


CREATE TABLE prm_detail
(
  prm_detail_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  payment_system character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与体系コード
  activate_date date NOT NULL, -- 有効日
  detail_title character varying(40) NOT NULL DEFAULT ''::character varying, -- 明細書タイトル
  company_name_export_type integer NOT NULL DEFAULT 0, -- 会社名出力区分
  section_name_export_type integer NOT NULL DEFAULT 0, -- 所属名出力区分
  message_export_type integer NOT NULL DEFAULT 0, -- メッセージ出力区分
  payment_export_type integer NOT NULL DEFAULT 0, -- 支給日出力区分
  section_code_export_type integer NOT NULL DEFAULT 0, -- 所属コード出力区分
  employee_number_export_type integer NOT NULL DEFAULT 0, -- 社員番号出力区分
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_detail_pkey PRIMARY KEY (prm_detail_id )
)
;
COMMENT ON TABLE prm_detail IS '支給明細マスタ';
COMMENT ON COLUMN prm_detail.prm_detail_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_detail.company_code IS '会社コード';
COMMENT ON COLUMN prm_detail.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_detail.payment_system IS '給与体系コード';
COMMENT ON COLUMN prm_detail.activate_date IS '有効日';
COMMENT ON COLUMN prm_detail.detail_title IS '明細書タイトル';
COMMENT ON COLUMN prm_detail.company_name_export_type IS '会社名出力区分';
COMMENT ON COLUMN prm_detail.section_name_export_type IS '所属名出力区分';
COMMENT ON COLUMN prm_detail.message_export_type IS 'メッセージ出力区分';
COMMENT ON COLUMN prm_detail.payment_export_type IS '支給日出力区分';
COMMENT ON COLUMN prm_detail.section_code_export_type IS '所属コード出力区分';
COMMENT ON COLUMN prm_detail.employee_number_export_type IS '社員番号出力区分';
COMMENT ON COLUMN prm_detail.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_detail.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_detail.insert_date IS '登録日';
COMMENT ON COLUMN prm_detail.insert_user IS '登録者';
COMMENT ON COLUMN prm_detail.update_date IS '更新日';
COMMENT ON COLUMN prm_detail.update_user IS '更新者';


CREATE TABLE prm_detail_item
(
  prm_detail_item_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  payroll_system character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与体系
  activate_date date NOT NULL, -- 有効日
  divide_number character varying(3) NOT NULL DEFAULT ''::character varying, -- 位置番号
  item_name character varying(10) NOT NULL DEFAULT ''::character varying, -- 項目名称
  item_name_print_type integer NOT NULL DEFAULT 0, -- 項目名称印刷区分
  item_name_view_type integer NOT NULL DEFAULT 0, -- 項目名表示区分
  item_number_view_type integer NOT NULL DEFAULT 0, -- 項目数値表示区分
  item_decimal_view_type integer NOT NULL DEFAULT 0, -- 項目小数表示区分
  item_number_1 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO1
  item_number_2 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO2
  item_number_3 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO3
  item_number_4 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO4
  item_number_5 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO5
  item_number_6 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO6
  item_number_7 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO7
  item_number_8 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO8
  item_number_9 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO9
  item_number_10 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO10
  item_number_11 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO11
  item_number_12 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO12
  item_number_13 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO13
  item_number_14 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO14
  item_number_15 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO15
  item_number_16 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO16
  item_number_17 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO17
  item_number_18 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO18
  item_number_19 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO19
  item_number_20 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO20
  item_number_21 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO21
  item_number_22 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO22
  item_number_23 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO23
  item_number_24 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO24
  item_number_25 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO25
  item_number_26 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO26
  item_number_27 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO27
  item_number_28 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO28
  item_number_29 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO29
  item_number_30 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO30
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_detail_item_pkey PRIMARY KEY (prm_detail_item_id )
)
;
COMMENT ON TABLE prm_detail_item IS '支給明細項目マスタ';
COMMENT ON COLUMN prm_detail_item.prm_detail_item_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_detail_item.company_code IS '会社コード';
COMMENT ON COLUMN prm_detail_item.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_detail_item.payroll_system IS '給与体系';
COMMENT ON COLUMN prm_detail_item.activate_date IS '有効日';
COMMENT ON COLUMN prm_detail_item.divide_number IS '位置番号';
COMMENT ON COLUMN prm_detail_item.item_name IS '項目名称';
COMMENT ON COLUMN prm_detail_item.item_name_print_type IS '項目名称印刷区分';
COMMENT ON COLUMN prm_detail_item.item_name_view_type IS '項目名表示区分';
COMMENT ON COLUMN prm_detail_item.item_number_view_type IS '項目数値表示区分';
COMMENT ON COLUMN prm_detail_item.item_decimal_view_type IS '項目小数表示区分';
COMMENT ON COLUMN prm_detail_item.item_number_1 IS '項目NO1';
COMMENT ON COLUMN prm_detail_item.item_number_2 IS '項目NO2';
COMMENT ON COLUMN prm_detail_item.item_number_3 IS '項目NO3';
COMMENT ON COLUMN prm_detail_item.item_number_4 IS '項目NO4';
COMMENT ON COLUMN prm_detail_item.item_number_5 IS '項目NO5';
COMMENT ON COLUMN prm_detail_item.item_number_6 IS '項目NO6';
COMMENT ON COLUMN prm_detail_item.item_number_7 IS '項目NO7';
COMMENT ON COLUMN prm_detail_item.item_number_8 IS '項目NO8';
COMMENT ON COLUMN prm_detail_item.item_number_9 IS '項目NO9';
COMMENT ON COLUMN prm_detail_item.item_number_10 IS '項目NO10';
COMMENT ON COLUMN prm_detail_item.item_number_11 IS '項目NO11';
COMMENT ON COLUMN prm_detail_item.item_number_12 IS '項目NO12';
COMMENT ON COLUMN prm_detail_item.item_number_13 IS '項目NO13';
COMMENT ON COLUMN prm_detail_item.item_number_14 IS '項目NO14';
COMMENT ON COLUMN prm_detail_item.item_number_15 IS '項目NO15';
COMMENT ON COLUMN prm_detail_item.item_number_16 IS '項目NO16';
COMMENT ON COLUMN prm_detail_item.item_number_17 IS '項目NO17';
COMMENT ON COLUMN prm_detail_item.item_number_18 IS '項目NO18';
COMMENT ON COLUMN prm_detail_item.item_number_19 IS '項目NO19';
COMMENT ON COLUMN prm_detail_item.item_number_20 IS '項目NO20';
COMMENT ON COLUMN prm_detail_item.item_number_21 IS '項目NO21';
COMMENT ON COLUMN prm_detail_item.item_number_22 IS '項目NO22';
COMMENT ON COLUMN prm_detail_item.item_number_23 IS '項目NO23';
COMMENT ON COLUMN prm_detail_item.item_number_24 IS '項目NO24';
COMMENT ON COLUMN prm_detail_item.item_number_25 IS '項目NO25';
COMMENT ON COLUMN prm_detail_item.item_number_26 IS '項目NO26';
COMMENT ON COLUMN prm_detail_item.item_number_27 IS '項目NO27';
COMMENT ON COLUMN prm_detail_item.item_number_28 IS '項目NO28';
COMMENT ON COLUMN prm_detail_item.item_number_29 IS '項目NO29';
COMMENT ON COLUMN prm_detail_item.item_number_30 IS '項目NO30';
COMMENT ON COLUMN prm_detail_item.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_detail_item.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_detail_item.insert_date IS '登録日';
COMMENT ON COLUMN prm_detail_item.insert_user IS '登録者';
COMMENT ON COLUMN prm_detail_item.update_date IS '更新日';
COMMENT ON COLUMN prm_detail_item.update_user IS '更新者';


CREATE TABLE prm_employee_pension_grade
(
  prm_employee_pension_grade_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  above integer NOT NULL DEFAULT 0, -- 以上
  grade integer NOT NULL DEFAULT 0, -- 等級
  basic_reward integer NOT NULL DEFAULT 0, -- 標準報酬月額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_employee_pension_grade_pkey PRIMARY KEY (prm_employee_pension_grade_id )
)
;
COMMENT ON TABLE prm_employee_pension_grade IS '厚生年金等級マスタ';
COMMENT ON COLUMN prm_employee_pension_grade.prm_employee_pension_grade_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_employee_pension_grade.above IS '以上';
COMMENT ON COLUMN prm_employee_pension_grade.grade IS '等級';
COMMENT ON COLUMN prm_employee_pension_grade.basic_reward IS '標準報酬月額';
COMMENT ON COLUMN prm_employee_pension_grade.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_employee_pension_grade.insert_date IS '登録日';
COMMENT ON COLUMN prm_employee_pension_grade.insert_user IS '登録者';
COMMENT ON COLUMN prm_employee_pension_grade.update_date IS '更新日';
COMMENT ON COLUMN prm_employee_pension_grade.update_user IS '更新者';


CREATE TABLE prm_exchange_bank
(
  prm_exchange_bank_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  exchange_code character varying(2) NOT NULL DEFAULT ''::character varying, -- 仕向コード
  exchange_name character varying(20) NOT NULL DEFAULT ''::character varying, -- 仕向名
  bank_code character varying(4) NOT NULL DEFAULT ''::character varying, -- 銀行コード
  branch_code character varying(3) NOT NULL DEFAULT ''::character varying, -- 支店コード
  deposit_type character varying(1) NOT NULL DEFAULT ''::character varying, -- 預金種別
  account_no character varying(7) NOT NULL DEFAULT ''::character varying, -- 口座番号
  client_no character varying(10) NOT NULL DEFAULT ''::character varying, -- 振込依頼人NO
  resident_tax_no character varying(10) NOT NULL DEFAULT ''::character varying, -- 住民税振込依頼NO
  client_name character varying(40) NOT NULL DEFAULT ''::character varying, -- 依頼人名漢字
  client_kana character varying(40) NOT NULL DEFAULT ''::character varying, -- 依頼人名ｶﾅ
  client_address_name character varying(60) NOT NULL DEFAULT ''::character varying, -- 依頼人住所漢字
  client_address_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- 依頼人住所ｶﾅ
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_exchange_bank_pkey PRIMARY KEY (prm_exchange_bank_id )
)
;
COMMENT ON TABLE prm_exchange_bank IS '仕向銀行マスタ';
COMMENT ON COLUMN prm_exchange_bank.prm_exchange_bank_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_exchange_bank.company_code IS '会社コード';
COMMENT ON COLUMN prm_exchange_bank.exchange_code IS '仕向コード';
COMMENT ON COLUMN prm_exchange_bank.exchange_name IS '仕向名';
COMMENT ON COLUMN prm_exchange_bank.bank_code IS '銀行コード';
COMMENT ON COLUMN prm_exchange_bank.branch_code IS '支店コード';
COMMENT ON COLUMN prm_exchange_bank.deposit_type IS '預金種別';
COMMENT ON COLUMN prm_exchange_bank.account_no IS '口座番号';
COMMENT ON COLUMN prm_exchange_bank.client_no IS '振込依頼人NO';
COMMENT ON COLUMN prm_exchange_bank.resident_tax_no IS '住民税振込依頼NO';
COMMENT ON COLUMN prm_exchange_bank.client_name IS '依頼人名漢字';
COMMENT ON COLUMN prm_exchange_bank.client_kana IS '依頼人名ｶﾅ';
COMMENT ON COLUMN prm_exchange_bank.client_address_name IS '依頼人住所漢字';
COMMENT ON COLUMN prm_exchange_bank.client_address_kana IS '依頼人住所ｶﾅ';
COMMENT ON COLUMN prm_exchange_bank.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_exchange_bank.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_exchange_bank.insert_date IS '登録日';
COMMENT ON COLUMN prm_exchange_bank.insert_user IS '登録者';
COMMENT ON COLUMN prm_exchange_bank.update_date IS '更新日';
COMMENT ON COLUMN prm_exchange_bank.update_user IS '更新者';


CREATE TABLE prm_health_insurance_grade
(
  prm_health_insurance_grade_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  above integer NOT NULL DEFAULT 0, -- 以上
  grade integer NOT NULL DEFAULT 0, -- 等級
  basic_reward integer NOT NULL DEFAULT 0, -- 標準報酬月額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_health_insurance_grade_pkey PRIMARY KEY (prm_health_insurance_grade_id )
)
;
COMMENT ON TABLE prm_health_insurance_grade IS '健康保険等級マスタ';
COMMENT ON COLUMN prm_health_insurance_grade.prm_health_insurance_grade_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_health_insurance_grade.above IS '以上';
COMMENT ON COLUMN prm_health_insurance_grade.grade IS '等級';
COMMENT ON COLUMN prm_health_insurance_grade.basic_reward IS '標準報酬月額';
COMMENT ON COLUMN prm_health_insurance_grade.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_health_insurance_grade.insert_date IS '登録日';
COMMENT ON COLUMN prm_health_insurance_grade.insert_user IS '登録者';
COMMENT ON COLUMN prm_health_insurance_grade.update_date IS '更新日';
COMMENT ON COLUMN prm_health_insurance_grade.update_user IS '更新者';


CREATE TABLE prm_health_office
(
  prm_health_office_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  activate_date date NOT NULL, -- 有効日
  health_office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 健保厚年事業所コード
  health_office_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 健保厚年事業所名
  office_mark_1 character varying(4) NOT NULL DEFAULT ''::character varying, -- 事業所整理記号1
  office_mark_2 character varying(8) NOT NULL DEFAULT ''::character varying, -- 事業所整理記号2
  office_number character varying(5) NOT NULL DEFAULT ''::character varying, -- 事業所番号
  calc_type integer NOT NULL DEFAULT 0, -- 計算方式
  postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying, -- 郵便番号1
  postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 郵便番号2
  prefecture character varying(10) NOT NULL DEFAULT ''::character varying, -- 都道府県
  address_1 character varying(50) NOT NULL DEFAULT ''::character varying, -- 市区町村
  address_2 character varying(50) NOT NULL DEFAULT ''::character varying, -- 番地
  address_3 character varying(50) NOT NULL DEFAULT ''::character varying, -- 建物情報
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying, -- 電話番号1
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号2
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号3
  owner_full_name character varying(30) NOT NULL DEFAULT ''::character varying, -- 事業主氏名
  owner_name character varying(30) NOT NULL DEFAULT ''::character varying, -- 事業主名称
  health_rate_all double precision NOT NULL DEFAULT 0, -- 健保料率給与全体
  health_rate_single double precision NOT NULL DEFAULT 0, -- 健保料率給与個人
  health_care_rate_all double precision NOT NULL DEFAULT 0, -- 健介料率給与全体
  health_care_rate_single double precision NOT NULL DEFAULT 0, -- 健介料率給与個人
  care_rate_all double precision NOT NULL DEFAULT 0, -- 介護料率給与全体
  care_rate_single double precision NOT NULL DEFAULT 0, -- 介護料率給与個人
  basic_rate_all double precision NOT NULL DEFAULT 0, -- 基本料率給与全体
  basic_rate_single double precision NOT NULL DEFAULT 0, -- 基本料率給与個人
  specific_rate_all double precision NOT NULL DEFAULT 0, -- 特定料率給与全体
  specific_rate_single double precision NOT NULL DEFAULT 0, -- 特定料率給与個人
  employee_rate_all double precision NOT NULL DEFAULT 0, -- 厚年料率給与全体
  employee_rate_single double precision NOT NULL DEFAULT 0, -- 厚年料率給与個人
  fund_rate_all double precision NOT NULL DEFAULT 0, -- 基金料率給与全体
  fund_rate_single double precision NOT NULL DEFAULT 0, -- 基金料率給与個人
  child_rate_all double precision NOT NULL DEFAULT 0, -- 児童料率給与全体
  health_fraction_all integer NOT NULL DEFAULT 0, -- 健保端数給与全体
  health_fraction_single integer NOT NULL DEFAULT 0, -- 健保端数給与個人
  health_care_fraction_all integer NOT NULL DEFAULT 0, -- 健介端数給与全体
  health_care_fraction_single integer NOT NULL DEFAULT 0, -- 健介端数給与個人
  care_fraction_all integer NOT NULL DEFAULT 0, -- 介護端数給与全体
  care_fraction_single integer NOT NULL DEFAULT 0, -- 介護端数給与個人
  basic_fraction_single integer NOT NULL DEFAULT 0, -- 基本端数給与個人
  employee_fraction_all integer NOT NULL DEFAULT 0, -- 厚年端数給与全体
  employee_fraction_single integer NOT NULL DEFAULT 0, -- 厚年端数給与個人
  fund_fraction_all integer NOT NULL DEFAULT 0, -- 基金端数給与全体
  fund_fraction_single integer NOT NULL DEFAULT 0, -- 基金端数給与個人
  child_fraction_all integer NOT NULL DEFAULT 0, -- 児童端数給与全体
  bonus_health_limit integer NOT NULL DEFAULT 0, -- 標準賞与健保上限
  bonus_employee_limit integer NOT NULL DEFAULT 0, -- 標準賞与厚年上限
  health_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 健保料率賞与全体
  health_bonus_rate_single double precision NOT NULL DEFAULT 0, -- 健保料率賞与個人
  health_care_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 健介料率賞与全体
  health_care_bonus_rate_single double precision NOT NULL DEFAULT 0, -- 健介料率賞与個人
  care_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 介護料率賞与全体
  care_bonus_rate_single double precision NOT NULL DEFAULT 0, -- 介護料率賞与個人
  basic_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 基本料率賞与全体
  basic_bonus_rate_single double precision NOT NULL DEFAULT 0, -- 基本料率賞与個人
  specific_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 特定料率賞与全体
  specific_bonus_rate_single double precision NOT NULL DEFAULT 0, -- 特定料率賞与個人
  employee_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 厚年料率賞与全体
  employee_bonus_rate_single double precision NOT NULL DEFAULT 0, -- 厚年料率賞与個人
  fund_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 基金料率賞与全体
  fund_bonus_rate_single double precision NOT NULL DEFAULT 0, -- 基金料率賞与個人
  child_bonus_rate_all double precision NOT NULL DEFAULT 0, -- 児童料率賞与全体
  health_bonus_fraction_all integer NOT NULL DEFAULT 0, -- 健保端数賞与全体
  health_bonus_fraction_single integer NOT NULL DEFAULT 0, -- 健保端数賞与個人
  health_care_bonus_fraction_all integer NOT NULL DEFAULT 0, -- 健介端数賞与全体
  health_care_bonus_fraction_single integer NOT NULL DEFAULT 0, -- 健介端数賞与個人
  care_bonus_fraction_all integer NOT NULL DEFAULT 0, -- 介護端数賞与全体
  care_bonus_fraction_single integer NOT NULL DEFAULT 0, -- 介護端数賞与個人
  basic_bonus_fraction_single integer NOT NULL DEFAULT 0, -- 基本端数賞与個人
  employee_bonus_fraction_all integer NOT NULL DEFAULT 0, -- 厚年端数賞与全体
  employee_bonus_fraction_single integer NOT NULL DEFAULT 0, -- 厚年端数賞与個人
  fund_bonus_fraction_all integer NOT NULL DEFAULT 0, -- 基金端数賞与全体
  fund_bonus_fraction_single integer NOT NULL DEFAULT 0, -- 基金端数賞与個人
  child_bonus_fraction_all integer NOT NULL DEFAULT 0, -- 児童端数賞与全体
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_health_office_pkey PRIMARY KEY (prm_health_office_id )
)
;
COMMENT ON TABLE prm_health_office IS '健保厚年事業所マスタ';
COMMENT ON COLUMN prm_health_office.prm_health_office_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_health_office.company_code IS '会社コード';
COMMENT ON COLUMN prm_health_office.activate_date IS '有効日';
COMMENT ON COLUMN prm_health_office.health_office_code IS '健保厚年事業所コード';
COMMENT ON COLUMN prm_health_office.health_office_name IS '健保厚年事業所名';
COMMENT ON COLUMN prm_health_office.office_mark_1 IS '事業所整理記号1';
COMMENT ON COLUMN prm_health_office.office_mark_2 IS '事業所整理記号2';
COMMENT ON COLUMN prm_health_office.office_number IS '事業所番号';
COMMENT ON COLUMN prm_health_office.calc_type IS '計算方式';
COMMENT ON COLUMN prm_health_office.postal_code_1 IS '郵便番号1';
COMMENT ON COLUMN prm_health_office.postal_code_2 IS '郵便番号2';
COMMENT ON COLUMN prm_health_office.prefecture IS '都道府県';
COMMENT ON COLUMN prm_health_office.address_1 IS '市区町村';
COMMENT ON COLUMN prm_health_office.address_2 IS '番地';
COMMENT ON COLUMN prm_health_office.address_3 IS '建物情報';
COMMENT ON COLUMN prm_health_office.phone_number_1 IS '電話番号1';
COMMENT ON COLUMN prm_health_office.phone_number_2 IS '電話番号2';
COMMENT ON COLUMN prm_health_office.phone_number_3 IS '電話番号3';
COMMENT ON COLUMN prm_health_office.owner_full_name IS '事業主氏名';
COMMENT ON COLUMN prm_health_office.owner_name IS '事業主名称';
COMMENT ON COLUMN prm_health_office.health_rate_all IS '健保料率給与全体';
COMMENT ON COLUMN prm_health_office.health_rate_single IS '健保料率給与個人';
COMMENT ON COLUMN prm_health_office.health_care_rate_all IS '健介料率給与全体';
COMMENT ON COLUMN prm_health_office.health_care_rate_single IS '健介料率給与個人';
COMMENT ON COLUMN prm_health_office.care_rate_all IS '介護料率給与全体';
COMMENT ON COLUMN prm_health_office.care_rate_single IS '介護料率給与個人';
COMMENT ON COLUMN prm_health_office.basic_rate_all IS '基本料率給与全体';
COMMENT ON COLUMN prm_health_office.basic_rate_single IS '基本料率給与個人';
COMMENT ON COLUMN prm_health_office.specific_rate_all IS '特定料率給与全体';
COMMENT ON COLUMN prm_health_office.specific_rate_single IS '特定料率給与個人';
COMMENT ON COLUMN prm_health_office.employee_rate_all IS '厚年料率給与全体';
COMMENT ON COLUMN prm_health_office.employee_rate_single IS '厚年料率給与個人';
COMMENT ON COLUMN prm_health_office.fund_rate_all IS '基金料率給与全体';
COMMENT ON COLUMN prm_health_office.fund_rate_single IS '基金料率給与個人';
COMMENT ON COLUMN prm_health_office.child_rate_all IS '児童料率給与全体';
COMMENT ON COLUMN prm_health_office.health_fraction_all IS '健保端数給与全体';
COMMENT ON COLUMN prm_health_office.health_fraction_single IS '健保端数給与個人';
COMMENT ON COLUMN prm_health_office.health_care_fraction_all IS '健介端数給与全体';
COMMENT ON COLUMN prm_health_office.health_care_fraction_single IS '健介端数給与個人';
COMMENT ON COLUMN prm_health_office.care_fraction_all IS '介護端数給与全体';
COMMENT ON COLUMN prm_health_office.care_fraction_single IS '介護端数給与個人';
COMMENT ON COLUMN prm_health_office.basic_fraction_single IS '基本端数給与個人';
COMMENT ON COLUMN prm_health_office.employee_fraction_all IS '厚年端数給与全体';
COMMENT ON COLUMN prm_health_office.employee_fraction_single IS '厚年端数給与個人';
COMMENT ON COLUMN prm_health_office.fund_fraction_all IS '基金端数給与全体';
COMMENT ON COLUMN prm_health_office.fund_fraction_single IS '基金端数給与個人';
COMMENT ON COLUMN prm_health_office.child_fraction_all IS '児童端数給与全体';
COMMENT ON COLUMN prm_health_office.bonus_health_limit IS '標準賞与健保上限';
COMMENT ON COLUMN prm_health_office.bonus_employee_limit IS '標準賞与厚年上限';
COMMENT ON COLUMN prm_health_office.health_bonus_rate_all IS '健保料率賞与全体';
COMMENT ON COLUMN prm_health_office.health_bonus_rate_single IS '健保料率賞与個人';
COMMENT ON COLUMN prm_health_office.health_care_bonus_rate_all IS '健介料率賞与全体';
COMMENT ON COLUMN prm_health_office.health_care_bonus_rate_single IS '健介料率賞与個人';
COMMENT ON COLUMN prm_health_office.care_bonus_rate_all IS '介護料率賞与全体';
COMMENT ON COLUMN prm_health_office.care_bonus_rate_single IS '介護料率賞与個人';
COMMENT ON COLUMN prm_health_office.basic_bonus_rate_all IS '基本料率賞与全体';
COMMENT ON COLUMN prm_health_office.basic_bonus_rate_single IS '基本料率賞与個人';
COMMENT ON COLUMN prm_health_office.specific_bonus_rate_all IS '特定料率賞与全体';
COMMENT ON COLUMN prm_health_office.specific_bonus_rate_single IS '特定料率賞与個人';
COMMENT ON COLUMN prm_health_office.employee_bonus_rate_all IS '厚年料率賞与全体';
COMMENT ON COLUMN prm_health_office.employee_bonus_rate_single IS '厚年料率賞与個人';
COMMENT ON COLUMN prm_health_office.fund_bonus_rate_all IS '基金料率賞与全体';
COMMENT ON COLUMN prm_health_office.fund_bonus_rate_single IS '基金料率賞与個人';
COMMENT ON COLUMN prm_health_office.child_bonus_rate_all IS '児童料率賞与全体';
COMMENT ON COLUMN prm_health_office.health_bonus_fraction_all IS '健保端数賞与全体';
COMMENT ON COLUMN prm_health_office.health_bonus_fraction_single IS '健保端数賞与個人';
COMMENT ON COLUMN prm_health_office.health_care_bonus_fraction_all IS '健介端数賞与全体';
COMMENT ON COLUMN prm_health_office.health_care_bonus_fraction_single IS '健介端数賞与個人';
COMMENT ON COLUMN prm_health_office.care_bonus_fraction_all IS '介護端数賞与全体';
COMMENT ON COLUMN prm_health_office.care_bonus_fraction_single IS '介護端数賞与個人';
COMMENT ON COLUMN prm_health_office.basic_bonus_fraction_single IS '基本端数賞与個人';
COMMENT ON COLUMN prm_health_office.employee_bonus_fraction_all IS '厚年端数賞与全体';
COMMENT ON COLUMN prm_health_office.employee_bonus_fraction_single IS '厚年端数賞与個人';
COMMENT ON COLUMN prm_health_office.fund_bonus_fraction_all IS '基金端数賞与全体';
COMMENT ON COLUMN prm_health_office.fund_bonus_fraction_single IS '基金端数賞与個人';
COMMENT ON COLUMN prm_health_office.child_bonus_fraction_all IS '児童端数賞与全体';
COMMENT ON COLUMN prm_health_office.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_health_office.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_health_office.insert_date IS '登録日';
COMMENT ON COLUMN prm_health_office.insert_user IS '登録者';
COMMENT ON COLUMN prm_health_office.update_date IS '更新日';
COMMENT ON COLUMN prm_health_office.update_user IS '更新者';


CREATE TABLE prm_labor_management
(
  prm_labor_management_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  activate_date date NOT NULL, -- 有効日
  section_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 所属コード
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 担当者コード
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying, -- 電話番号1
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号2
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号3
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_labor_management_pkey PRIMARY KEY (prm_labor_management_id)
)
;
COMMENT ON TABLE prm_labor_management IS '労務担当者マスタ';
COMMENT ON COLUMN prm_labor_management.prm_labor_management_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_labor_management.company_code IS '会社コード';
COMMENT ON COLUMN prm_labor_management.activate_date IS '有効日';
COMMENT ON COLUMN prm_labor_management.section_code IS '所属コード';
COMMENT ON COLUMN prm_labor_management.personal_id IS '担当者コード';
COMMENT ON COLUMN prm_labor_management.phone_number_1 IS '電話番号1';
COMMENT ON COLUMN prm_labor_management.phone_number_2 IS '電話番号2';
COMMENT ON COLUMN prm_labor_management.phone_number_3 IS '電話番号3';
COMMENT ON COLUMN prm_labor_management.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_labor_management.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_labor_management.insert_date IS '登録日';
COMMENT ON COLUMN prm_labor_management.insert_user IS '登録者';
COMMENT ON COLUMN prm_labor_management.update_date IS '更新日';
COMMENT ON COLUMN prm_labor_management.update_user IS '更新者';


CREATE TABLE prm_labor_office
(
  prm_labor_office_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  activate_date date NOT NULL, -- 有効日
  labor_office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 労働保険事業所コード
  employment_office_number character varying(20) NOT NULL DEFAULT ''::character varying, -- 雇用保険事業所番号
  labor_office_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 労働保険事業所名
  postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying, -- 郵便番号1
  postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 郵便番号2
  prefecture character varying(10) NOT NULL DEFAULT ''::character varying, -- 都道府県
  address_1 character varying(50) NOT NULL DEFAULT ''::character varying, -- 市区町村
  address_2 character varying(50) NOT NULL DEFAULT ''::character varying, -- 番地
  address_3 character varying(50) NOT NULL DEFAULT ''::character varying, -- 建物情報
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying, -- 電話番号1
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号2
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号3
  owner_full_name character varying(30) NOT NULL DEFAULT ''::character varying, -- 事業主氏名
  owner_name character varying(30) NOT NULL DEFAULT ''::character varying, -- 事業主名称
  employment_rate_all double precision NOT NULL DEFAULT 0, -- 雇保料率全体
  employment_rate_single double precision NOT NULL DEFAULT 0, -- 雇保料率個人
  accident_rate_all double precision NOT NULL DEFAULT 0, -- 労災料率全体
  employment_fraction_all integer NOT NULL DEFAULT 0, -- 雇保端数全体
  employment_fraction_single integer NOT NULL DEFAULT 0, -- 雇保端数個人
  accident_fraction_all integer NOT NULL DEFAULT 0, -- 労災端数全体
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_labor_office_pkey PRIMARY KEY (prm_labor_office_id )
)
;
COMMENT ON TABLE prm_labor_office IS '労働保険事業所マスタ';
COMMENT ON COLUMN prm_labor_office.prm_labor_office_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_labor_office.company_code IS '会社コード';
COMMENT ON COLUMN prm_labor_office.activate_date IS '有効日';
COMMENT ON COLUMN prm_labor_office.labor_office_code IS '労働保険事業所コード';
COMMENT ON COLUMN prm_labor_office.employment_office_number IS '雇用保険事業所番号';
COMMENT ON COLUMN prm_labor_office.labor_office_name IS '労働保険事業所名';
COMMENT ON COLUMN prm_labor_office.postal_code_1 IS '郵便番号1';
COMMENT ON COLUMN prm_labor_office.postal_code_2 IS '郵便番号2';
COMMENT ON COLUMN prm_labor_office.prefecture IS '都道府県';
COMMENT ON COLUMN prm_labor_office.address_1 IS '市区町村';
COMMENT ON COLUMN prm_labor_office.address_2 IS '番地';
COMMENT ON COLUMN prm_labor_office.address_3 IS '建物情報';
COMMENT ON COLUMN prm_labor_office.phone_number_1 IS '電話番号1';
COMMENT ON COLUMN prm_labor_office.phone_number_2 IS '電話番号2';
COMMENT ON COLUMN prm_labor_office.phone_number_3 IS '電話番号3';
COMMENT ON COLUMN prm_labor_office.owner_full_name IS '事業主氏名';
COMMENT ON COLUMN prm_labor_office.owner_name IS '事業主名称';
COMMENT ON COLUMN prm_labor_office.employment_rate_all IS '雇保料率全体';
COMMENT ON COLUMN prm_labor_office.employment_rate_single IS '雇保料率個人';
COMMENT ON COLUMN prm_labor_office.accident_rate_all IS '労災料率全体';
COMMENT ON COLUMN prm_labor_office.employment_fraction_all IS '雇保端数全体';
COMMENT ON COLUMN prm_labor_office.employment_fraction_single IS '雇保端数個人';
COMMENT ON COLUMN prm_labor_office.accident_fraction_all IS '労災端数全体';
COMMENT ON COLUMN prm_labor_office.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_labor_office.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_labor_office.insert_date IS '登録日';
COMMENT ON COLUMN prm_labor_office.insert_user IS '登録者';
COMMENT ON COLUMN prm_labor_office.update_date IS '更新日';
COMMENT ON COLUMN prm_labor_office.update_user IS '更新者';


CREATE TABLE prm_message
(
  prm_message_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  payroll_message_code integer NOT NULL DEFAULT 1, -- メッセージコード
  payroll_message character varying(60) NOT NULL DEFAULT ''::character varying, -- メッセージ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_message_pkey PRIMARY KEY (prm_message_id )
)
;
COMMENT ON TABLE prm_message IS 'メッセージマスタ';
COMMENT ON COLUMN prm_message.prm_message_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_message.company_code IS '会社コード';
COMMENT ON COLUMN prm_message.payroll_message_code IS 'メッセージコード';
COMMENT ON COLUMN prm_message.payroll_message IS 'メッセージ';
COMMENT ON COLUMN prm_message.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_message.insert_date IS '登録日';
COMMENT ON COLUMN prm_message.insert_user IS '登録者';
COMMENT ON COLUMN prm_message.update_date IS '更新日';
COMMENT ON COLUMN prm_message.update_user IS '更新者';


CREATE TABLE prm_name_management
(
  prm_name_management_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  naming_type integer NOT NULL DEFAULT 0, -- 名称区分
  naming_name character varying(12) NOT NULL DEFAULT ''::character varying, -- 名称区分名
  naming_code_digit integer NOT NULL DEFAULT 1, -- コード桁数
  naming_name_digit integer NOT NULL DEFAULT 10, -- 名称桁数
  remark_digit integer NOT NULL DEFAULT 10, -- 備考桁数
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_name_management_pkey PRIMARY KEY (prm_name_management_id )
)
;
COMMENT ON TABLE prm_name_management IS '名称管理マスタ';
COMMENT ON COLUMN prm_name_management.prm_name_management_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_name_management.company_code IS '会社コード';
COMMENT ON COLUMN prm_name_management.naming_type IS '名称区分';
COMMENT ON COLUMN prm_name_management.naming_name IS '名称区分名';
COMMENT ON COLUMN prm_name_management.naming_code_digit IS 'コード桁数';
COMMENT ON COLUMN prm_name_management.naming_name_digit IS '名称桁数';
COMMENT ON COLUMN prm_name_management.remark_digit IS '備考桁数';
COMMENT ON COLUMN prm_name_management.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_name_management.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_name_management.insert_date IS '登録日';
COMMENT ON COLUMN prm_name_management.insert_user IS '登録者';
COMMENT ON COLUMN prm_name_management.update_date IS '更新日';
COMMENT ON COLUMN prm_name_management.update_user IS '更新者';


CREATE TABLE prm_naming
(
  prm_naming_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  naming_type integer NOT NULL DEFAULT 0, -- 名称区分
  naming_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 名称コード
  naming_name character varying(60) NOT NULL DEFAULT ''::character varying, -- 名称
  naming_remark character varying(60) NOT NULL DEFAULT ''::character varying, -- 備考
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_naming_pkey PRIMARY KEY (prm_naming_id )
)
;
COMMENT ON TABLE prm_naming IS '名称マスタ';
COMMENT ON COLUMN prm_naming.prm_naming_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_naming.company_code IS '会社コード';
COMMENT ON COLUMN prm_naming.naming_type IS '名称区分';
COMMENT ON COLUMN prm_naming.naming_code IS '名称コード';
COMMENT ON COLUMN prm_naming.naming_name IS '名称';
COMMENT ON COLUMN prm_naming.naming_remark IS '備考';
COMMENT ON COLUMN prm_naming.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_naming.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_naming.insert_date IS '登録日';
COMMENT ON COLUMN prm_naming.insert_user IS '登録者';
COMMENT ON COLUMN prm_naming.update_date IS '更新日';
COMMENT ON COLUMN prm_naming.update_user IS '更新者';


CREATE TABLE prm_office
(
  prm_office_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 事業所コード
  activate_date date NOT NULL, -- 有効日
  office_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 事業所名称
  health_office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 健保厚年事業所コード
  labor_office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 労働保険事業所コード
  remark character varying(80) NOT NULL DEFAULT ''::character varying, -- 備考
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_office_pkey PRIMARY KEY (prm_office_id )
)
;
COMMENT ON TABLE prm_office IS '事業所マスタ';
COMMENT ON COLUMN prm_office.prm_office_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_office.company_code IS '会社コード';
COMMENT ON COLUMN prm_office.office_code IS '事業所コード';
COMMENT ON COLUMN prm_office.activate_date IS '有効日';
COMMENT ON COLUMN prm_office.office_name IS '事業所名称';
COMMENT ON COLUMN prm_office.health_office_code IS '健保厚年事業所コード';
COMMENT ON COLUMN prm_office.labor_office_code IS '労働保険事業所コード';
COMMENT ON COLUMN prm_office.remark IS '備考';
COMMENT ON COLUMN prm_office.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_office.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_office.insert_date IS '登録日';
COMMENT ON COLUMN prm_office.insert_user IS '登録者';
COMMENT ON COLUMN prm_office.update_date IS '更新日';
COMMENT ON COLUMN prm_office.update_user IS '更新者';


CREATE TABLE prm_payroll_book
(
  prm_payroll_book_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  activate_date date NOT NULL, -- 有効日
  divide_number character varying(3) NOT NULL DEFAULT ''::character varying, -- 位置番号
  item_name character varying(12) NOT NULL DEFAULT ''::character varying, -- 項目名称
  item_number_1 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO1
  item_number_2 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO2
  item_number_3 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO3
  item_number_4 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO4
  item_number_5 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO5
  item_number_6 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO6
  item_number_7 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO7
  item_number_8 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO8
  item_number_9 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO9
  item_number_10 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO10
  item_number_11 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO11
  item_number_12 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO12
  item_number_13 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO13
  item_number_14 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO14
  item_number_15 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO15
  item_number_16 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO16
  item_number_17 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO17
  item_number_18 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO18
  item_number_19 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO19
  item_number_20 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO20
  item_number_21 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO21
  item_number_22 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO22
  item_number_23 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO23
  item_number_24 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO24
  item_number_25 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO25
  item_number_26 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO26
  item_number_27 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO27
  item_number_28 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO28
  item_number_29 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO29
  item_number_30 character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目NO30
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_book_pkey PRIMARY KEY (prm_payroll_book_id )
)
;
COMMENT ON TABLE prm_payroll_book IS '賃金台帳マスタ';
COMMENT ON COLUMN prm_payroll_book.prm_payroll_book_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_book.company_code IS '会社コード';
COMMENT ON COLUMN prm_payroll_book.activate_date IS '有効日';
COMMENT ON COLUMN prm_payroll_book.divide_number IS '位置番号';
COMMENT ON COLUMN prm_payroll_book.item_name IS '項目名称';
COMMENT ON COLUMN prm_payroll_book.item_number_1 IS '項目NO1';
COMMENT ON COLUMN prm_payroll_book.item_number_2 IS '項目NO2';
COMMENT ON COLUMN prm_payroll_book.item_number_3 IS '項目NO3';
COMMENT ON COLUMN prm_payroll_book.item_number_4 IS '項目NO4';
COMMENT ON COLUMN prm_payroll_book.item_number_5 IS '項目NO5';
COMMENT ON COLUMN prm_payroll_book.item_number_6 IS '項目NO6';
COMMENT ON COLUMN prm_payroll_book.item_number_7 IS '項目NO7';
COMMENT ON COLUMN prm_payroll_book.item_number_8 IS '項目NO8';
COMMENT ON COLUMN prm_payroll_book.item_number_9 IS '項目NO9';
COMMENT ON COLUMN prm_payroll_book.item_number_10 IS '項目NO10';
COMMENT ON COLUMN prm_payroll_book.item_number_11 IS '項目NO11';
COMMENT ON COLUMN prm_payroll_book.item_number_12 IS '項目NO12';
COMMENT ON COLUMN prm_payroll_book.item_number_13 IS '項目NO13';
COMMENT ON COLUMN prm_payroll_book.item_number_14 IS '項目NO14';
COMMENT ON COLUMN prm_payroll_book.item_number_15 IS '項目NO15';
COMMENT ON COLUMN prm_payroll_book.item_number_16 IS '項目NO16';
COMMENT ON COLUMN prm_payroll_book.item_number_17 IS '項目NO17';
COMMENT ON COLUMN prm_payroll_book.item_number_18 IS '項目NO18';
COMMENT ON COLUMN prm_payroll_book.item_number_19 IS '項目NO19';
COMMENT ON COLUMN prm_payroll_book.item_number_20 IS '項目NO20';
COMMENT ON COLUMN prm_payroll_book.item_number_21 IS '項目NO21';
COMMENT ON COLUMN prm_payroll_book.item_number_22 IS '項目NO22';
COMMENT ON COLUMN prm_payroll_book.item_number_23 IS '項目NO23';
COMMENT ON COLUMN prm_payroll_book.item_number_24 IS '項目NO24';
COMMENT ON COLUMN prm_payroll_book.item_number_25 IS '項目NO25';
COMMENT ON COLUMN prm_payroll_book.item_number_26 IS '項目NO26';
COMMENT ON COLUMN prm_payroll_book.item_number_27 IS '項目NO27';
COMMENT ON COLUMN prm_payroll_book.item_number_28 IS '項目NO28';
COMMENT ON COLUMN prm_payroll_book.item_number_29 IS '項目NO29';
COMMENT ON COLUMN prm_payroll_book.item_number_30 IS '項目NO30';
COMMENT ON COLUMN prm_payroll_book.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_payroll_book.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_book.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_book.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_book.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_book.update_user IS '更新者';


CREATE TABLE prm_payroll_human
(
  prm_payroll_human_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  activate_date date NOT NULL, -- 有効日
  birth_date date NOT NULL, -- 生年月日
  gender_type integer NOT NULL, -- 性別区分
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_human_pkey PRIMARY KEY (prm_payroll_human_id )
)
;
COMMENT ON TABLE prm_payroll_human IS '給与人事マスタ';
COMMENT ON COLUMN prm_payroll_human.prm_payroll_human_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_human.personal_id IS '個人ID';
COMMENT ON COLUMN prm_payroll_human.activate_date IS '有効日';
COMMENT ON COLUMN prm_payroll_human.birth_date IS '生年月日';
COMMENT ON COLUMN prm_payroll_human.gender_type IS '性別区分';
COMMENT ON COLUMN prm_payroll_human.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_human.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_human.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_human.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_human.update_user IS '更新者';


CREATE TABLE prm_payroll_income_deduction
(
  prm_payroll_income_deduction_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 対象年月
  k_income_basic_deduction integer NOT NULL DEFAULT 0, -- 甲欄所得基礎控除
  k_income_spouse_deduction integer NOT NULL DEFAULT 0, -- 甲欄所得配偶者控除
  k_income_dependent_deduction integer NOT NULL DEFAULT 0, -- 甲欄所得扶養控除
  o_income_dependent_deduction integer NOT NULL DEFAULT 0, -- 乙欄税額扶養控除
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_income_deduction_pkey PRIMARY KEY (prm_payroll_income_deduction_id )
)
;
COMMENT ON TABLE prm_payroll_income_deduction IS '給与所得税控除マスタ';
COMMENT ON COLUMN prm_payroll_income_deduction.prm_payroll_income_deduction_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_income_deduction.execute_date IS '対象年月';
COMMENT ON COLUMN prm_payroll_income_deduction.k_income_basic_deduction IS '甲欄所得基礎控除';
COMMENT ON COLUMN prm_payroll_income_deduction.k_income_spouse_deduction IS '甲欄所得配偶者控除';
COMMENT ON COLUMN prm_payroll_income_deduction.k_income_dependent_deduction IS '甲欄所得扶養控除';
COMMENT ON COLUMN prm_payroll_income_deduction.o_income_dependent_deduction IS '乙欄税額扶養控除';
COMMENT ON COLUMN prm_payroll_income_deduction.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_income_deduction.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_income_deduction.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_income_deduction.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_income_deduction.update_user IS '更新者';


CREATE TABLE prm_payroll_k_income_deduction
(
  prm_payroll_k_income_deduction_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 対象年月
  above integer NOT NULL DEFAULT 0, -- 以上
  deduction_rate integer NOT NULL DEFAULT 0, -- 控除率
  deduction_price integer NOT NULL DEFAULT 0, -- 控除額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_k_income_deduction_pkey PRIMARY KEY (prm_payroll_k_income_deduction_id )
)
;
COMMENT ON TABLE prm_payroll_k_income_deduction IS '給与甲欄所得控除マスタ';
COMMENT ON COLUMN prm_payroll_k_income_deduction.prm_payroll_k_income_deduction_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_k_income_deduction.execute_date IS '対象年月';
COMMENT ON COLUMN prm_payroll_k_income_deduction.above IS '以上';
COMMENT ON COLUMN prm_payroll_k_income_deduction.deduction_rate IS '控除率';
COMMENT ON COLUMN prm_payroll_k_income_deduction.deduction_price IS '控除額';
COMMENT ON COLUMN prm_payroll_k_income_deduction.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_k_income_deduction.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_k_income_deduction.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_k_income_deduction.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_k_income_deduction.update_user IS '更新者';


CREATE TABLE prm_payroll_o_tax
(
  prm_payroll_o_tax_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 対象年月
  above integer NOT NULL DEFAULT 0, -- 以上
  tax_rate double precision NOT NULL DEFAULT 0, -- 税率
  tax_price integer NOT NULL DEFAULT 0, -- 税額
  excessive_price integer NOT NULL DEFAULT 0, -- 超過額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_o_tax_pkey PRIMARY KEY (prm_payroll_o_tax_id )
)
;
COMMENT ON TABLE prm_payroll_o_tax IS '給与乙欄税額マスタ';
COMMENT ON COLUMN prm_payroll_o_tax.prm_payroll_o_tax_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_o_tax.execute_date IS '対象年月';
COMMENT ON COLUMN prm_payroll_o_tax.above IS '以上';
COMMENT ON COLUMN prm_payroll_o_tax.tax_rate IS '税率';
COMMENT ON COLUMN prm_payroll_o_tax.tax_price IS '税額';
COMMENT ON COLUMN prm_payroll_o_tax.excessive_price IS '超過額';
COMMENT ON COLUMN prm_payroll_o_tax.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_o_tax.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_o_tax.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_o_tax.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_o_tax.update_user IS '更新者';


CREATE TABLE prm_payroll_scale
(
  prm_payroll_scale_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL, -- 会社コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  item_number character varying(3) NOT NULL, -- 項目番号
  payroll_code character varying(10) NOT NULL, -- 賃金コード
  activate_date date NOT NULL, -- 有効日
  payroll_name character varying(30) NOT NULL, -- 賃金名称
  scale_price double precision NOT NULL DEFAULT 0, -- 金額
  scale_rate double precision NOT NULL DEFAULT 0, -- 率
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_scale_pkey PRIMARY KEY (prm_payroll_scale_id )
)
;
COMMENT ON TABLE prm_payroll_scale IS '賃金表マスタ';
COMMENT ON COLUMN prm_payroll_scale.prm_payroll_scale_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_scale.company_code IS '会社コード';
COMMENT ON COLUMN prm_payroll_scale.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_payroll_scale.item_number IS '項目番号';
COMMENT ON COLUMN prm_payroll_scale.payroll_code IS '賃金コード';
COMMENT ON COLUMN prm_payroll_scale.activate_date IS '有効日';
COMMENT ON COLUMN prm_payroll_scale.payroll_name IS '賃金名称';
COMMENT ON COLUMN prm_payroll_scale.scale_price IS '金額';
COMMENT ON COLUMN prm_payroll_scale.scale_rate IS '率';
COMMENT ON COLUMN prm_payroll_scale.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_payroll_scale.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_scale.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_scale.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_scale.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_scale.update_user IS '更新者';


CREATE TABLE prm_payroll_section
(
  prm_payroll_section_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  section_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 所属コード
  activate_date date NOT NULL, -- 有効日
  office_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 事業所コード
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_section_pkey PRIMARY KEY (prm_payroll_section_id )
)
;
COMMENT ON TABLE prm_payroll_section IS '給与関連所属マスタ';
COMMENT ON COLUMN prm_payroll_section.prm_payroll_section_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_section.company_code IS '会社コード';
COMMENT ON COLUMN prm_payroll_section.section_code IS '所属コード';
COMMENT ON COLUMN prm_payroll_section.activate_date IS '有効日';
COMMENT ON COLUMN prm_payroll_section.office_code IS '事業所コード';
COMMENT ON COLUMN prm_payroll_section.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_payroll_section.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_section.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_section.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_section.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_section.update_user IS '更新者';


CREATE TABLE prm_payroll_system
(
  prm_payroll_system_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL, -- 会社コード
  payroll_system_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与体系コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  activate_date date NOT NULL, -- 有効日
  payroll_system_name character varying(20) NOT NULL DEFAULT ''::character varying, -- 給与体系名称
  payroll_data_transfer_date date NOT NULL, -- 給振データ振込日
  bonus_data_transfer_date date NOT NULL, -- 賞振データ振込日
  extra_data character varying(20) NOT NULL DEFAULT '',	-- 追加データ
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_system_pkey PRIMARY KEY (prm_payroll_system_id )
)
;
COMMENT ON TABLE prm_payroll_system IS '給与体系マスタ';
COMMENT ON COLUMN prm_payroll_system.prm_payroll_system_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_system.company_code IS '会社コード';
COMMENT ON COLUMN prm_payroll_system.payroll_system_code IS '給与体系コード';
COMMENT ON COLUMN prm_payroll_system.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_payroll_system.activate_date IS '有効日';
COMMENT ON COLUMN prm_payroll_system.payroll_system_name IS '給与体系名称';
COMMENT ON COLUMN prm_payroll_system.payroll_data_transfer_date IS '給振データ振込日';
COMMENT ON COLUMN prm_payroll_system.bonus_data_transfer_date IS '賞振データ振込日';
COMMENT ON COLUMN prm_payroll_system.extra_data IS '追加データ';
COMMENT ON COLUMN prm_payroll_system.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_payroll_system.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_system.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_system.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_system.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_system.update_user IS '更新者';


CREATE TABLE prm_payroll_system_item
(
  prm_payroll_system_item_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL, -- 会社コード
  payroll_system_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与体系コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  activate_date date NOT NULL, -- 有効日
  item_number character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目番号
  import_type integer NOT NULL DEFAULT 0, -- 取込区分
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_system_item_pkey PRIMARY KEY (prm_payroll_system_item_id )
)
;
COMMENT ON TABLE prm_payroll_system_item IS '給与体系項目マスタ';
COMMENT ON COLUMN prm_payroll_system_item.prm_payroll_system_item_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_system_item.company_code IS '会社コード';
COMMENT ON COLUMN prm_payroll_system_item.payroll_system_code IS '給与体系コード';
COMMENT ON COLUMN prm_payroll_system_item.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_payroll_system_item.activate_date IS '有効日';
COMMENT ON COLUMN prm_payroll_system_item.item_number IS '項目番号';
COMMENT ON COLUMN prm_payroll_system_item.import_type IS '取込区分';
COMMENT ON COLUMN prm_payroll_system_item.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_system_item.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_system_item.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_system_item.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_system_item.update_user IS '更新者';


CREATE TABLE prm_payroll_tax
(
  prm_payroll_tax_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  execute_date date NOT NULL, -- 対象年月
  above integer NOT NULL DEFAULT 0, -- 以上
  tax_rate double precision NOT NULL DEFAULT 0, -- 税率
  tax_price integer NOT NULL DEFAULT 0, -- 税控除額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_tax_pkey PRIMARY KEY (prm_payroll_tax_id )
)
;
COMMENT ON TABLE prm_payroll_tax IS '給与甲欄税額マスタ';
COMMENT ON COLUMN prm_payroll_tax.prm_payroll_tax_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_tax.execute_date IS '対象年月';
COMMENT ON COLUMN prm_payroll_tax.above IS '以上';
COMMENT ON COLUMN prm_payroll_tax.tax_rate IS '税率';
COMMENT ON COLUMN prm_payroll_tax.tax_price IS '税控除額';
COMMENT ON COLUMN prm_payroll_tax.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_tax.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_tax.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_tax.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_tax.update_user IS '更新者';


CREATE TABLE prm_payroll_type_rate
(
  prm_payroll_type_rate_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  payroll_type integer NOT NULL DEFAULT 0, -- 給与区分
  overtime_basic_hour double precision NOT NULL DEFAULT 0, -- 残業基準時間数
  overtime_basic_day double precision NOT NULL DEFAULT 0, -- 残業基準日数
  overtime_rate double precision NOT NULL DEFAULT 0, -- 普通残業掛率
  latetime_rate double precision NOT NULL DEFAULT 0, -- 深夜残業掛率
  legal_overtime_rate double precision NOT NULL DEFAULT 0, -- 法休普通残業掛率
  legal_latetime_rate double precision NOT NULL DEFAULT 0, -- 法休深夜残業掛率
  specific_overtime_rate double precision NOT NULL DEFAULT 0, -- 所休普通残業掛率
  specific_latetime_rate double precision NOT NULL DEFAULT 0, -- 所休深夜残業掛率
  fortyfive_overtime_rate double precision NOT NULL DEFAULT 0, -- 45時間超割増率
  sixty_overtime_rate double precision NOT NULL DEFAULT 0, -- 60時間超割増率
  late_early_basic_hour double precision NOT NULL DEFAULT 0, -- 遅早控除基準時間数
  late_early_basic_day double precision NOT NULL DEFAULT 0, -- 遅早控除基準日数
  absence_basic_day double precision NOT NULL DEFAULT 0, -- 欠勤控除基準日数
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_payroll_type_rate_pkey PRIMARY KEY (prm_payroll_type_rate_id )
)
;
COMMENT ON TABLE prm_payroll_type_rate IS '給与区分別率マスタ';
COMMENT ON COLUMN prm_payroll_type_rate.prm_payroll_type_rate_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_payroll_type_rate.company_code IS '会社コード';
COMMENT ON COLUMN prm_payroll_type_rate.payroll_type IS '給与区分';
COMMENT ON COLUMN prm_payroll_type_rate.overtime_basic_hour IS '残業基準時間数';
COMMENT ON COLUMN prm_payroll_type_rate.overtime_basic_day IS '残業基準日数';
COMMENT ON COLUMN prm_payroll_type_rate.overtime_rate IS '普通残業掛率';
COMMENT ON COLUMN prm_payroll_type_rate.latetime_rate IS '深夜残業掛率';
COMMENT ON COLUMN prm_payroll_type_rate.legal_overtime_rate IS '法休普通残業掛率';
COMMENT ON COLUMN prm_payroll_type_rate.legal_latetime_rate IS '法休深夜残業掛率';
COMMENT ON COLUMN prm_payroll_type_rate.specific_overtime_rate IS '所休普通残業掛率';
COMMENT ON COLUMN prm_payroll_type_rate.specific_latetime_rate IS '所休深夜残業掛率';
COMMENT ON COLUMN prm_payroll_type_rate.fortyfive_overtime_rate IS '45時間超割増率';
COMMENT ON COLUMN prm_payroll_type_rate.sixty_overtime_rate IS '60時間超割増率';
COMMENT ON COLUMN prm_payroll_type_rate.late_early_basic_hour IS '遅早控除基準時間数';
COMMENT ON COLUMN prm_payroll_type_rate.late_early_basic_day IS '遅早控除基準日数';
COMMENT ON COLUMN prm_payroll_type_rate.absence_basic_day IS '欠勤控除基準日数';
COMMENT ON COLUMN prm_payroll_type_rate.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_payroll_type_rate.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_payroll_type_rate.insert_date IS '登録日';
COMMENT ON COLUMN prm_payroll_type_rate.insert_user IS '登録者';
COMMENT ON COLUMN prm_payroll_type_rate.update_date IS '更新日';
COMMENT ON COLUMN prm_payroll_type_rate.update_user IS '更新者';


CREATE TABLE prm_personal_address
(
  prm_personal_address_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  resident_date date NOT NULL, -- 転入年月日
  local_public_code character varying(6) NOT NULL DEFAULT ''::character varying, -- 地方公共団体コード
  postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying, -- 郵便番号1
  postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 郵便番号2
  prefecture character varying(10) NOT NULL DEFAULT ''::character varying, -- 都道府県
  address_1 character varying(50) NOT NULL DEFAULT ''::character varying, -- 市区町村
  address_2 character varying(50) NOT NULL DEFAULT ''::character varying, -- 番地
  address_3 character varying(50) NOT NULL DEFAULT ''::character varying, -- 建物情報
  address_kana character varying(50) NOT NULL DEFAULT ''::character varying, -- 住所フリガナ
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying, -- 電話番号1
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号2
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying, -- 電話番号3
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_address_pkey PRIMARY KEY (prm_personal_address_id )
)
;
COMMENT ON TABLE prm_personal_address IS '個人住所情報';
COMMENT ON COLUMN prm_personal_address.prm_personal_address_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_address.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_address.resident_date IS '転入年月日';
COMMENT ON COLUMN prm_personal_address.local_public_code IS '地方公共団体コード';
COMMENT ON COLUMN prm_personal_address.postal_code_1 IS '郵便番号1';
COMMENT ON COLUMN prm_personal_address.postal_code_2 IS '郵便番号2';
COMMENT ON COLUMN prm_personal_address.prefecture IS '都道府県';
COMMENT ON COLUMN prm_personal_address.address_1 IS '市区町村';
COMMENT ON COLUMN prm_personal_address.address_2 IS '番地';
COMMENT ON COLUMN prm_personal_address.address_3 IS '建物情報';
COMMENT ON COLUMN prm_personal_address.address_kana IS '住所フリガナ';
COMMENT ON COLUMN prm_personal_address.phone_number_1 IS '電話番号1';
COMMENT ON COLUMN prm_personal_address.phone_number_2 IS '電話番号2';
COMMENT ON COLUMN prm_personal_address.phone_number_3 IS '電話番号3';
COMMENT ON COLUMN prm_personal_address.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_address.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_address.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_address.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_address.update_user IS '更新者';


CREATE TABLE prm_personal_dependent
(
  prm_personal_dependent_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  spouse_type integer NOT NULL DEFAULT 0, -- 配偶者区分
  spouse_age_type integer NOT NULL DEFAULT 0, -- 配偶者老人区分
  spouse_disability_type integer NOT NULL DEFAULT 0, -- 配偶者障害区分
  dependent integer NOT NULL DEFAULT 0, -- 一般扶養者数
  specific_dependent integer NOT NULL DEFAULT 0, -- 特定扶養者数
  child_dependent integer NOT NULL DEFAULT 0, -- 年少扶養者数
  age_dependent integer NOT NULL DEFAULT 0, -- 老人扶養者数
  together_dependent integer NOT NULL DEFAULT 0, -- 同居老親人数
  disability integer NOT NULL DEFAULT 0, -- 一般障害者数
  special_disability integer NOT NULL DEFAULT 0, -- 特別障害者数
  together_special_disability integer NOT NULL DEFAULT 0, -- 同居特別障害者数
  total_dependent integer NOT NULL DEFAULT 0, -- 扶養人数合計(本人扶養除く)
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_dependent_pkey PRIMARY KEY (prm_personal_dependent_id )
)
;
COMMENT ON TABLE prm_personal_dependent IS '個人税扶養情報';
COMMENT ON COLUMN prm_personal_dependent.prm_personal_dependent_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_dependent.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_dependent.spouse_type IS '配偶者区分';
COMMENT ON COLUMN prm_personal_dependent.spouse_age_type IS '配偶者老人区分';
COMMENT ON COLUMN prm_personal_dependent.spouse_disability_type IS '配偶者障害区分';
COMMENT ON COLUMN prm_personal_dependent.dependent IS '一般扶養者数';
COMMENT ON COLUMN prm_personal_dependent.specific_dependent IS '特定扶養者数';
COMMENT ON COLUMN prm_personal_dependent.child_dependent IS '年少扶養者数';
COMMENT ON COLUMN prm_personal_dependent.age_dependent IS '老人扶養者数';
COMMENT ON COLUMN prm_personal_dependent.together_dependent IS '同居老親人数';
COMMENT ON COLUMN prm_personal_dependent.disability IS '一般障害者数';
COMMENT ON COLUMN prm_personal_dependent.special_disability IS '特別障害者数';
COMMENT ON COLUMN prm_personal_dependent.together_special_disability IS '同居特別障害者数';
COMMENT ON COLUMN prm_personal_dependent.total_dependent IS '扶養人数合計(本人扶養除く)';
COMMENT ON COLUMN prm_personal_dependent.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_dependent.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_dependent.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_dependent.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_dependent.update_user IS '更新者';

CREATE TABLE prm_personal_dependent_detail
(
 prm_personal_dependent_detail_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
 personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
 dependent_seq integer NOT NULL DEFAULT 0, -- 扶養者連番
 family_relationship_type integer NOT NULL DEFAULT 0, -- 続柄
 family_relationship_other character varying(10) DEFAULT ''::character varying, -- 続柄（詳細）
 last_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 姓
 first_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 名
 last_kana character varying(50), -- カナ姓
 first_kana character varying(50), -- カナ名
 birth_date date NOT NULL, -- 生年月日
 gender_type integer NOT NULL DEFAULT 1, -- 性別区分
 together_type integer NOT NULL DEFAULT 1, -- 同居区分
 address_1 character varying(50) DEFAULT ''::character varying, -- 市区町村
 address_2 character varying(50) DEFAULT ''::character varying, -- 番地
 address_3 character varying(50) DEFAULT ''::character varying, -- 建物情報
 disability_type integer NOT NULL DEFAULT 0, -- 障害区分
 income_promising_frame integer NOT NULL DEFAULT 0, -- 所得見込額
 change_date date, -- 異動年月日
 dependent_start_date date, -- 扶養開始日
 dependent_end_date date, -- 扶養終了日
 resident_type integer NOT NULL DEFAULT 0, -- 居住者区分
 health_card_distribute_type integer NOT NULL DEFAULT 0, -- 保険証配布
 health_card_collect_type integer NOT NULL DEFAULT 0, -- 保険証回収
 source_relation_type integer NOT NULL DEFAULT 0, -- 源泉続柄
 source_national_type integer NOT NULL DEFAULT 0, -- 居住者区分
 delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
 insert_date timestamp without time zone NOT NULL, -- 登録日
 insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
 update_date timestamp without time zone NOT NULL, -- 更新日
 update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
 constraint prm_personal_dependent_detail_pkey primary key (prm_personal_dependent_detail_id)
)
;

COMMENT ON TABLE prm_personal_dependent_detail IS '個人扶養者情報';
COMMENT ON COLUMN prm_personal_dependent_detail.prm_personal_dependent_detail_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_dependent_detail.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_dependent_detail.dependent_seq IS '扶養者連番';
COMMENT ON COLUMN prm_personal_dependent_detail.family_relationship_type IS '続柄';
COMMENT ON COLUMN prm_personal_dependent_detail.family_relationship_other IS '続柄（詳細）';
COMMENT ON COLUMN prm_personal_dependent_detail.last_name IS '姓';
COMMENT ON COLUMN prm_personal_dependent_detail.first_name IS '名';
COMMENT ON COLUMN prm_personal_dependent_detail.last_kana IS 'カナ姓';
COMMENT ON COLUMN prm_personal_dependent_detail.first_kana IS 'カナ名';
COMMENT ON COLUMN prm_personal_dependent_detail.birth_date IS '生年月日';
COMMENT ON COLUMN prm_personal_dependent_detail.gender_type IS '性別区分';
COMMENT ON COLUMN prm_personal_dependent_detail.together_type IS '同居区分';
COMMENT ON COLUMN prm_personal_dependent_detail.address_1 IS '市区町村';
COMMENT ON COLUMN prm_personal_dependent_detail.address_2 IS '番地';
COMMENT ON COLUMN prm_personal_dependent_detail.address_3 IS '建物情報';
COMMENT ON COLUMN prm_personal_dependent_detail.disability_type IS '障害区分';
COMMENT ON COLUMN prm_personal_dependent_detail.income_promising_frame IS '所得見込額';
COMMENT ON COLUMN prm_personal_dependent_detail.change_date IS '異動年月日';
COMMENT ON COLUMN prm_personal_dependent_detail.dependent_start_date IS '扶養開始日';
COMMENT ON COLUMN prm_personal_dependent_detail.dependent_end_date IS '扶養終了日';
COMMENT ON COLUMN prm_personal_dependent_detail.resident_type IS '居住者区分';
COMMENT ON COLUMN prm_personal_dependent_detail.health_card_distribute_type IS '保険証配布';
COMMENT ON COLUMN prm_personal_dependent_detail.health_card_collect_type IS '保険証回収';
COMMENT ON COLUMN prm_personal_dependent_detail.source_relation_type IS '源泉続柄';
COMMENT ON COLUMN prm_personal_dependent_detail.source_national_type IS '居住者区分';
COMMENT ON COLUMN prm_personal_dependent_detail.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_dependent_detail.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_dependent_detail.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_dependent_detail.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_dependent_detail.update_user IS '更新者';


CREATE TABLE prm_personal_fixed_deduction
(
  prm_personal_fixed_deduction_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  item_number character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目番号
  company_code character varying(3) NOT NULL DEFAULT ''::character varying, -- 取扱会社コード
  serial_number integer NOT NULL DEFAULT 0, -- 連番
  payroll_fixed_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与時固定控除コード
  payroll_fixed_price integer NOT NULL DEFAULT 0, -- 給与時固定控除額
  payroll_first_price integer NOT NULL DEFAULT 0, -- 給与時初回控除額
  payroll_last_price integer NOT NULL DEFAULT 0, -- 給与時最終控除額
  payroll_start_date date NOT NULL, -- 給与時控除開始年月
  payroll_end_date date NOT NULL, -- 給与時控除満了年月
  bonus_fixed_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 賞与時固定控除コード
  bonus_fixed_price integer NOT NULL DEFAULT 0, -- 賞与時固定控除額
  bonus_first_price integer NOT NULL DEFAULT 0, -- 賞与時初回控除額
  bonus_last_price integer NOT NULL DEFAULT 0, -- 賞与時最終控除額
  bonus_start_date date NOT NULL, -- 賞与時控除開始年月
  bonus_end_date date NOT NULL, -- 賞与時控除満了年月
  bond_number character varying(20) NOT NULL DEFAULT ''::character varying, -- 証券番号
  insurance_type character varying(3) NOT NULL DEFAULT ''::character varying, -- 保険種類
  insurance_support_price integer NOT NULL DEFAULT 0, -- 保険料補助額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_fixed_deduction_pkey PRIMARY KEY (prm_personal_fixed_deduction_id )
)
;
COMMENT ON TABLE prm_personal_fixed_deduction IS '個人固定控除情報';
COMMENT ON COLUMN prm_personal_fixed_deduction.prm_personal_fixed_deduction_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_fixed_deduction.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_fixed_deduction.item_number IS '項目番号';
COMMENT ON COLUMN prm_personal_fixed_deduction.company_code IS '取扱会社コード';
COMMENT ON COLUMN prm_personal_fixed_deduction.serial_number IS '連番';
COMMENT ON COLUMN prm_personal_fixed_deduction.payroll_fixed_code IS '給与時固定控除コード';
COMMENT ON COLUMN prm_personal_fixed_deduction.payroll_fixed_price IS '給与時固定控除額';
COMMENT ON COLUMN prm_personal_fixed_deduction.payroll_first_price IS '給与時初回控除額';
COMMENT ON COLUMN prm_personal_fixed_deduction.payroll_last_price IS '給与時最終控除額';
COMMENT ON COLUMN prm_personal_fixed_deduction.payroll_start_date IS '給与時控除開始年月';
COMMENT ON COLUMN prm_personal_fixed_deduction.payroll_end_date IS '給与時控除満了年月';
COMMENT ON COLUMN prm_personal_fixed_deduction.bonus_fixed_code IS '賞与時固定控除コード';
COMMENT ON COLUMN prm_personal_fixed_deduction.bonus_fixed_price IS '賞与時固定控除額';
COMMENT ON COLUMN prm_personal_fixed_deduction.bonus_first_price IS '賞与時初回控除額';
COMMENT ON COLUMN prm_personal_fixed_deduction.bonus_last_price IS '賞与時最終控除額';
COMMENT ON COLUMN prm_personal_fixed_deduction.bonus_start_date IS '賞与時控除開始年月';
COMMENT ON COLUMN prm_personal_fixed_deduction.bonus_end_date IS '賞与時控除満了年月';
COMMENT ON COLUMN prm_personal_fixed_deduction.bond_number IS '証券番号';
COMMENT ON COLUMN prm_personal_fixed_deduction.insurance_type IS '保険種類';
COMMENT ON COLUMN prm_personal_fixed_deduction.insurance_support_price IS '保険料補助額';
COMMENT ON COLUMN prm_personal_fixed_deduction.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_fixed_deduction.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_fixed_deduction.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_fixed_deduction.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_fixed_deduction.update_user IS '更新者';


CREATE TABLE prm_personal_fixed_payment
(
  prm_personal_fixed_payment_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  item_number character varying(3) NOT NULL DEFAULT 0, -- 項目番号
  serial_number integer NOT NULL DEFAULT 0, -- 連番
  fixed_payment_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 固定支給コード
  fixed_payment_price integer NOT NULL DEFAULT 0, -- 固定支給額
  first_payment_price integer NOT NULL DEFAULT 0, -- 初回支給額
  last_payment_price integer NOT NULL DEFAULT 0, -- 最終支給額
  payment_start_date date NOT NULL, -- 支給開始年月
  payment_end_date date NOT NULL, -- 支給終了年月
  remark character varying(60) NOT NULL DEFAULT ''::character varying, -- 備考
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_fixed_payment_pkey PRIMARY KEY (prm_personal_fixed_payment_id )
)
;
COMMENT ON TABLE prm_personal_fixed_payment IS '個人固定支給情報';
COMMENT ON COLUMN prm_personal_fixed_payment.prm_personal_fixed_payment_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_fixed_payment.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_fixed_payment.item_number IS '項目番号';
COMMENT ON COLUMN prm_personal_fixed_payment.serial_number IS '連番';
COMMENT ON COLUMN prm_personal_fixed_payment.fixed_payment_code IS '固定支給コード';
COMMENT ON COLUMN prm_personal_fixed_payment.fixed_payment_price IS '固定支給額';
COMMENT ON COLUMN prm_personal_fixed_payment.first_payment_price IS '初回支給額';
COMMENT ON COLUMN prm_personal_fixed_payment.last_payment_price IS '最終支給額';
COMMENT ON COLUMN prm_personal_fixed_payment.payment_start_date IS '支給開始年月';
COMMENT ON COLUMN prm_personal_fixed_payment.payment_end_date IS '支給終了年月';
COMMENT ON COLUMN prm_personal_fixed_payment.remark IS '備考';
COMMENT ON COLUMN prm_personal_fixed_payment.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_fixed_payment.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_fixed_payment.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_fixed_payment.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_fixed_payment.update_user IS '更新者';


CREATE TABLE prm_personal_labor
(
  prm_personal_labor_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  employment_calc_type integer NOT NULL DEFAULT 0, -- 雇用保険計算区分
  employment_age_type integer NOT NULL DEFAULT 0, -- 雇用保険高年齢区分
  labor_office character varying(10) NOT NULL DEFAULT ''::character varying, -- 労働保険事業所
  employment_number character varying(13) NOT NULL DEFAULT ''::character varying, -- 雇用保険番号
  employment_start_date date, -- 雇用保険資格取得日
  employment_end_date date, -- 雇用保険資格喪失日
  employment_lost_type integer NOT NULL DEFAULT 0, -- 雇用保険喪失区分
  accident_calc_type integer NOT NULL DEFAULT 1, -- 労災保険計算区分
  desk_receped_date date, -- 窓口受付年月日
  employment_lost_date date, -- 資格喪失処理済年月日
  unemployed_issued_date date, -- 離職票発行年月日
  employment_card_distribute_date date, -- 雇用保険被保険者証配布日
  employment_card_collect_date date, -- 雇用保険被保険者回収日
  employment_acquire_date date, -- 雇用保険取得処理済年月日
  employment_lost_finish_date date, -- 雇用保険喪失処理済年月日
  employment_remark character varying(30) NOT NULL DEFAULT ''::character varying, -- 備考
  acquir_type integer NOT NULL default 0, -- 取得区分
  insured_cause_type integer NOT NULL default 0, -- 被保険者となったことの原因
  personal_payroll_type integer NOT NULL default 0, -- 賃金（区分）
  payroll_price integer NOT NULL default 0, -- 賃金
  employment_contract_type integer NOT NULL default 0, -- 雇用形態
  job_category_type integer NOT NULL default 0, -- 職種
  contract_period_type integer NOT NULL default 0, -- 契約期間の定めの有無
  contract_start_date date, -- 契約期間自
  contract_end_date date, -- 契約期間至
  contract_article_type integer NOT NULL default 0, -- 契約更新条項の有無
  specific_work_time_hours integer NOT NULL default 0, -- 所定労働時間（時）
  specific_work_time_minutes integer NOT NULL default 0, -- 所定労働時間（分）
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_labor_pkey PRIMARY KEY (prm_personal_labor_id )
)
;
COMMENT ON TABLE prm_personal_labor IS '個人労働保険情報';
COMMENT ON COLUMN prm_personal_labor.prm_personal_labor_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_labor.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_labor.employment_calc_type IS '雇用保険計算区分';
COMMENT ON COLUMN prm_personal_labor.employment_age_type IS '雇用保険高年齢区分';
COMMENT ON COLUMN prm_personal_labor.labor_office IS '労働保険事業所';
COMMENT ON COLUMN prm_personal_labor.employment_number IS '雇用保険番号';
COMMENT ON COLUMN prm_personal_labor.employment_start_date IS '雇用保険資格取得日';
COMMENT ON COLUMN prm_personal_labor.employment_end_date IS '雇用保険資格喪失日';
COMMENT ON COLUMN prm_personal_labor.employment_lost_type IS '雇用保険喪失区分';
COMMENT ON COLUMN prm_personal_labor.accident_calc_type IS '労災保険計算区分';
COMMENT ON COLUMN prm_personal_labor.desk_receped_date is '窓口受付年月日';
COMMENT ON COLUMN prm_personal_labor.employment_lost_date is '資格喪失処理済年月日';
COMMENT ON COLUMN prm_personal_labor.unemployed_issued_date is '離職票発行年月日';
COMMENT ON COLUMN prm_personal_labor.employment_card_distribute_date is '雇用保険被保険者証配布日';
COMMENT ON COLUMN prm_personal_labor.employment_card_collect_date is '雇用保険被保険者証回収日';
COMMENT ON COLUMN prm_personal_labor.employment_acquire_date is '雇用保険取得処理済年月日';
COMMENT ON COLUMN prm_personal_labor.employment_lost_finish_date is '雇用保険喪失処理済年月日';
COMMENT ON COLUMN prm_personal_labor.employment_remark is '備考';
COMMENT ON COLUMN prm_personal_labor.acquir_type is '取得区分';
COMMENT ON COLUMN prm_personal_labor.insured_cause_type is '被保険者となったことの原因';
COMMENT ON COLUMN prm_personal_labor.personal_payroll_type is '賃金（区分）';
COMMENT ON COLUMN prm_personal_labor.payroll_price is '賃金';
COMMENT ON COLUMN prm_personal_labor.employment_contract_type is '雇用形態';
COMMENT ON COLUMN prm_personal_labor.job_category_type is '職種';
COMMENT ON COLUMN prm_personal_labor.contract_period_type is '契約期間の定めの有無';
COMMENT ON COLUMN prm_personal_labor.contract_start_date is '契約期間自';
COMMENT ON COLUMN prm_personal_labor.contract_end_date is '契約期間至';
COMMENT ON COLUMN prm_personal_labor.contract_article_type is '契約更新条項の有無';
COMMENT ON COLUMN prm_personal_labor.specific_work_time_hours is '所定労働時間（時）';
COMMENT ON COLUMN prm_personal_labor.specific_work_time_minutes is '所定労働時間（分）';
COMMENT ON COLUMN prm_personal_labor.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_labor.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_labor.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_labor.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_labor.update_user IS '更新者';


CREATE TABLE prm_personal_payroll
(
  prm_personal_payroll_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  payroll_system_type character varying(10) NOT NULL DEFAULT ''::character varying, -- 給与体系区分
  bonus_system_type character varying(10) NOT NULL DEFAULT ''::character varying, -- 賞与体系区分
  payroll_type integer NOT NULL DEFAULT 1, -- 給与区分
  payroll_calc_type integer NOT NULL DEFAULT 0, -- 給与計算区分
  bonus_calc_type integer NOT NULL DEFAULT 0, -- 賞与計算区分
  adjustment_calc_type integer NOT NULL DEFAULT 0, -- 年末調整計算区分
  tax_table_type integer NOT NULL DEFAULT 1, -- 税表区分
  payroll_tax_type integer NOT NULL DEFAULT 1, -- 給与指定税率区分
  payroll_tax_rate double precision NOT NULL DEFAULT 0, -- 給与指定税率
  payroll_tax_price integer NOT NULL DEFAULT 0, -- 給与指定税額
  bonus_tax_type integer NOT NULL DEFAULT 1, -- 賞与指定税率区分
  bonus_tax_rate double precision NOT NULL DEFAULT 0, -- 賞与指定税率
  bonus_tax_price integer NOT NULL DEFAULT 0, -- 賞与指定税額
  minor_type integer NOT NULL DEFAULT 0, -- 未成年区分
  widow_type integer NOT NULL DEFAULT 0, -- 寡婦区分
  disability_type integer NOT NULL DEFAULT 0, -- 障害者区分
  student_type integer NOT NULL DEFAULT 0, -- 学生区分
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_payroll_pkey PRIMARY KEY (prm_personal_payroll_id )
)
;
COMMENT ON TABLE prm_personal_payroll IS '個人給与情報';
COMMENT ON COLUMN prm_personal_payroll.prm_personal_payroll_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_payroll.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_payroll.payroll_system_type IS '給与体系区分';
COMMENT ON COLUMN prm_personal_payroll.bonus_system_type IS '賞与体系区分';
COMMENT ON COLUMN prm_personal_payroll.payroll_type IS '給与区分';
COMMENT ON COLUMN prm_personal_payroll.payroll_calc_type IS '給与計算区分';
COMMENT ON COLUMN prm_personal_payroll.bonus_calc_type IS '賞与計算区分';
COMMENT ON COLUMN prm_personal_payroll.adjustment_calc_type IS '年末調整計算区分';
COMMENT ON COLUMN prm_personal_payroll.tax_table_type IS '税表区分';
COMMENT ON COLUMN prm_personal_payroll.payroll_tax_type IS '給与指定税率区分';
COMMENT ON COLUMN prm_personal_payroll.payroll_tax_rate IS '給与指定税率';
COMMENT ON COLUMN prm_personal_payroll.payroll_tax_price IS '給与指定税額';
COMMENT ON COLUMN prm_personal_payroll.bonus_tax_type IS '賞与指定税率区分';
COMMENT ON COLUMN prm_personal_payroll.bonus_tax_rate IS '賞与指定税率';
COMMENT ON COLUMN prm_personal_payroll.bonus_tax_price IS '賞与指定税額';
COMMENT ON COLUMN prm_personal_payroll.minor_type IS '未成年区分';
COMMENT ON COLUMN prm_personal_payroll.widow_type IS '寡婦区分';
COMMENT ON COLUMN prm_personal_payroll.disability_type IS '障害者区分';
COMMENT ON COLUMN prm_personal_payroll.student_type IS '学生区分';
COMMENT ON COLUMN prm_personal_payroll.company_code IS '会社コード';
COMMENT ON COLUMN prm_personal_payroll.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_payroll.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_payroll.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_payroll.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_payroll.update_user IS '更新者';


CREATE TABLE prm_personal_resident
(
  prm_personal_resident_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  apply_fiscal_year date NOT NULL, -- 適用年度
  city_code character varying(6) NOT NULL DEFAULT ''::character varying, -- 市区町村コード
  levy_tax_june integer NOT NULL DEFAULT 0, -- 徴収税額6月
  levy_tax_july integer NOT NULL DEFAULT 0, -- 徴収税額7月
  levy_tax_august integer NOT NULL DEFAULT 0, -- 徴収税額8月
  levy_tax_september integer NOT NULL DEFAULT 0, -- 徴収税額9月
  levy_tax_october integer NOT NULL DEFAULT 0, -- 徴収税額10月
  levy_tax_november integer NOT NULL DEFAULT 0, -- 徴収税額11月
  levy_tax_december integer NOT NULL DEFAULT 0, -- 徴収税額12月
  levy_tax_january integer NOT NULL DEFAULT 0, -- 徴収税額1月
  levy_tax_february integer NOT NULL DEFAULT 0, -- 徴収税額2月
  levy_tax_march integer NOT NULL DEFAULT 0, -- 徴収税額3月
  levy_tax_april integer NOT NULL DEFAULT 0, -- 徴収税額4月
  levy_tax_may integer NOT NULL DEFAULT 0, -- 徴収税額5月
  collect_type integer NOT NULL DEFAULT 0, -- 徴収方法
  collect_reason_type integer NOT NULL DEFAULT 0, -- 一括徴収の理由
  uncollect_reason_type integer NOT NULL DEFAULT 0, -- 一括徴収ができない理由
  uncollect_reason character varying(100) NOT NULL DEFAULT ''::character varying, -- 理由
  change_report_date date, -- 異動届出年月日
  original_family_name character varying(50) NOT NULL DEFAULT ''::character varying, -- 給与所得者(旧姓)
  payment_limit_date date, -- 納期限分
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_resident_pkey PRIMARY KEY (prm_personal_resident_id)
)
;
COMMENT ON TABLE prm_personal_resident IS '個人住民税情報';
COMMENT ON COLUMN prm_personal_resident.prm_personal_resident_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_resident.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_resident.apply_fiscal_year IS '適用年度';
COMMENT ON COLUMN prm_personal_resident.city_code IS '市区町村コード';
COMMENT ON COLUMN prm_personal_resident.levy_tax_june IS '徴収税額6月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_july IS '徴収税額7月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_august IS '徴収税額8月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_september IS '徴収税額9月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_october IS '徴収税額10月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_november IS '徴収税額11月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_december IS '徴収税額12月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_january IS '徴収税額1月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_february IS '徴収税額2月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_march IS '徴収税額3月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_april IS '徴収税額4月';
COMMENT ON COLUMN prm_personal_resident.levy_tax_may IS '徴収税額5月';
COMMENT ON COLUMN prm_personal_resident.collect_type IS '徴収方法';
COMMENT ON COLUMN prm_personal_resident.collect_reason_type IS '一括徴収の理由';
COMMENT ON COLUMN prm_personal_resident.uncollect_reason_type IS '一括徴収ができない理由';
COMMENT ON COLUMN prm_personal_resident.uncollect_reason IS '理由';
COMMENT ON COLUMN prm_personal_resident.change_report_date IS '異動届出年月日';
COMMENT ON COLUMN prm_personal_resident.original_family_name IS '給与所得者(旧姓)';
COMMENT ON COLUMN prm_personal_resident.payment_limit_date IS '納期限分';
COMMENT ON COLUMN prm_personal_resident.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_resident.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_resident.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_resident.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_resident.update_user IS '更新者';


CREATE TABLE prm_personal_social
(
  prm_personal_social_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  childcare_type integer NOT NULL DEFAULT 0, -- 育児休業区分
  social_short_labor_type integer NOT NULL DEFAULT 0, -- 社保短時間就労者区分
  oldperson_employee_type integer NOT NULL DEFAULT 0, -- 高齢被用者区分
  care_calc_type integer NOT NULL DEFAULT 0, -- 介護保険計算区分
  basic_pension_number character varying(10) NOT NULL DEFAULT ''::character varying, -- 基礎年金番号
  insurance_calc_type integer NOT NULL DEFAULT 0, -- 保険料算出区分
  health_office character varying(10) NOT NULL DEFAULT ''::character varying, -- 健康保険事業所
  childcare_start_date date, -- 育児休業開始日
  childcare_end_date date, -- 育児休業終了日
  childcare_status_type integer NOT NULL default 0, -- 育児新規延長
  childcare_start_processed_date date, -- 育児休業開始処理済年月日
  childcare_end_processed_date date, -- 育児休業終了処理済年月日
  childcare_remark character varying(30) NOT NULL DEFAULT ''::character varying, -- 育児休業備考
  elderlycare_type integer NOT NULL default 0, -- 介護休業区分
  elderlycare_start_date date, -- 介護休業開始日
  elderlycare_end_date date, -- 介護休業終了日
  elderlycare_start_processed_date date, -- 介護休業開始処理済年月日
  elderlycare_end_processed_date date, -- 介護休業終了処理済年月日
  childbearing_type integer NOT NULL default 0, -- 出産休業区分
  childbearing_start_date date, -- 出産休業開始日
  childbearing_end_date date, -- 出産休業終了日
  childbearing_due_date date, -- 出産予定日
  childbearing_date date, -- 出産日
  childbearing_start_processed_date date, -- 出産休業開始処理済年月日
  health_calc_type integer NOT NULL DEFAULT 0, -- 健康保険計算区分
  health_card_number character varying(7) NOT NULL DEFAULT ''::character varying, -- 健康保険証番号
  health_lost_type integer NOT NULL DEFAULT 0, -- 健康保険喪失区分
  health_start_date date, -- 健康保険資格取得日
  health_end_date date, -- 健康保険資格喪失日
  health_reference_number character varying(10) NOT NULL DEFAULT ''::character varying, -- 被保険者整理番号
  health_card_collect_type integer NOT NULL default 0, -- 被保険者証回収区分
  health_aquired_type integer NOT NULL default 0, -- 資格取得区分
  health_dependent_type integer NOT NULL default 1, -- 被扶養者の有無
  disqualification_type integer NOT NULL default 0, -- 資格喪失原因
  health_acquire_date date, -- 健康保険取得処理済年月日
  health_lost_date date, -- 健康保険喪失処理済年月日
  health_card_distribute_date date, -- 健康保険証配布日
  health_card_collect_date date, -- 健康保険証回収日
  oldperson_card_distribute_date date, -- 高齢者受給証配布日
  oldperson_card_collect_date date, -- 高齢者受給証回収日
  social_remark character varying(30) NOT NULL DEFAULT ''::character varying, -- 備考
  employee_calc_type integer NOT NULL DEFAULT 0, -- 厚生年金計算区分
  employment_type integer NOT NULL DEFAULT 0, -- 厚生年金種別
  employment_number character varying(10) NOT NULL DEFAULT ''::character varying, -- 厚生年金番号
  employment_lost_type integer NOT NULL DEFAULT 0, -- 厚生年金喪失区分
  employment_start_date date, -- 厚生年金資格取得日
  employment_end_date date, -- 厚生年金資格喪失日
  fund_number character varying(10) NOT NULL DEFAULT ''::character varying, -- 厚年基金加入者番号
  fund_start_date date, -- 厚年基金加入日
  fund_end_date date, -- 厚年基金脱退日
  fixed_health integer NOT NULL DEFAULT 0, -- 定額健康保険料
  fixed_care integer NOT NULL DEFAULT 0, -- 定額介護保険料
  fixed_employment integer NOT NULL DEFAULT 0, -- 定額厚生年金
  fixed_fund integer NOT NULL DEFAULT 0, -- 定額厚生年金基金
  fixed_owner_health integer NOT NULL DEFAULT 0, -- 定額事業主健康保険料
  fixed_owner_care integer NOT NULL DEFAULT 0, -- 定額事業主介護保険料
  fixed_owner_employment integer NOT NULL DEFAULT 0, -- 定額事業主厚生年金
  fixed_owner_fund integer NOT NULL DEFAULT 0, -- 定額事業主厚生年金基金
  fixed_owner_child integer NOT NULL DEFAULT 0, -- 定額事業主児童手当
  employment_acquire_date date, -- 厚生年金取得処理済年月日
  employment_lost_date date, -- 厚生年金喪失処理済年月日
  employment_diary_distribute_date date, -- 厚生年金手帳配布日
  employment_diary_collect_date date, -- 厚生年金手帳回収日
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_social_pkey PRIMARY KEY (prm_personal_social_id )
)
;
COMMENT ON TABLE prm_personal_social IS '個人社会保険情報';
COMMENT ON COLUMN prm_personal_social.prm_personal_social_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_social.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_social.childcare_type IS '育児休業区分';
COMMENT ON COLUMN prm_personal_social.social_short_labor_type IS '社保短時間就労者区分';
COMMENT ON COLUMN prm_personal_social.oldperson_employee_type IS '高齢被用者区分';
COMMENT ON COLUMN prm_personal_social.care_calc_type IS '介護保険計算区分';
COMMENT ON COLUMN prm_personal_social.basic_pension_number IS '基礎年金番号';
COMMENT ON COLUMN prm_personal_social.insurance_calc_type IS '保険料算出区分';
COMMENT ON COLUMN prm_personal_social.health_office IS '健康保険事業所';
COMMENT ON COLUMN prm_personal_social.childcare_start_date is '育児休業開始日';
COMMENT ON COLUMN prm_personal_social.childcare_end_date is '育児休業終了日';
COMMENT ON COLUMN prm_personal_social.childcare_status_type is '育児新規延長';
COMMENT ON COLUMN prm_personal_social.childcare_start_processed_date is '育児休業開始処理済年月日';
COMMENT ON COLUMN prm_personal_social.childcare_end_processed_date is '育児休業終了処理済年月日';
COMMENT ON COLUMN prm_personal_social.childcare_remark is '育児休業備考';
COMMENT ON COLUMN prm_personal_social.elderlycare_type is '介護休業区分';
COMMENT ON COLUMN prm_personal_social.elderlycare_start_date is '介護休業開始日';
COMMENT ON COLUMN prm_personal_social.elderlycare_end_date is '介護休業終了日';
COMMENT ON COLUMN prm_personal_social.elderlycare_start_processed_date is '介護休業開始処理済年月日';
COMMENT ON COLUMN prm_personal_social.elderlycare_end_processed_date is '介護休業終了処理済年月日';
COMMENT ON COLUMN prm_personal_social.childbearing_type is '出産休業区分';
COMMENT ON COLUMN prm_personal_social.childbearing_start_date is '出産休業開始日';
COMMENT ON COLUMN prm_personal_social.childbearing_end_date is '出産休業終了日';
COMMENT ON COLUMN prm_personal_social.childbearing_due_date is '出産予定日';
COMMENT ON COLUMN prm_personal_social.childbearing_date is '出産日';
COMMENT ON COLUMN prm_personal_social.childbearing_start_processed_date is '出産休業開始処理済年月日';
COMMENT ON COLUMN prm_personal_social.health_calc_type IS '健康保険計算区分';
COMMENT ON COLUMN prm_personal_social.health_card_number IS '健康保険証番号';
COMMENT ON COLUMN prm_personal_social.health_lost_type IS '健康保険喪失区分';
COMMENT ON COLUMN prm_personal_social.health_start_date IS '健康保険資格取得日';
COMMENT ON COLUMN prm_personal_social.health_end_date IS '健康保険資格喪失日';
COMMENT ON COLUMN prm_personal_social.health_reference_number is '被保険者整理番号';
COMMENT ON COLUMN prm_personal_social.health_card_collect_type is '被保険者証回収区分';
COMMENT ON COLUMN prm_personal_social.health_aquired_type is '資格取得区分';
COMMENT ON COLUMN prm_personal_social.health_dependent_type is '被扶養者の有無';
COMMENT ON COLUMN prm_personal_social.disqualification_type is '資格喪失原因';
COMMENT ON COLUMN prm_personal_social.health_acquire_date is '健康保険取得処理済年月日';
COMMENT ON COLUMN prm_personal_social.health_lost_date is '健康保険喪失処理済年月日';
COMMENT ON COLUMN prm_personal_social.health_card_distribute_date is '健康保険証配布日';
COMMENT ON COLUMN prm_personal_social.health_card_collect_date is '健康保険証回収日';
COMMENT ON COLUMN prm_personal_social.oldperson_card_distribute_date is '高齢者受給証配布日';
COMMENT ON COLUMN prm_personal_social.oldperson_card_collect_date is '高齢者受給証回収日';
COMMENT ON COLUMN prm_personal_social.social_remark is '備考';
COMMENT ON COLUMN prm_personal_social.employee_calc_type IS '厚生年金計算区分';
COMMENT ON COLUMN prm_personal_social.employment_type IS '厚生年金種別';
COMMENT ON COLUMN prm_personal_social.employment_number IS '厚生年金番号';
COMMENT ON COLUMN prm_personal_social.employment_lost_type IS '厚生年金喪失区分';
COMMENT ON COLUMN prm_personal_social.employment_start_date IS '厚生年金資格取得日';
COMMENT ON COLUMN prm_personal_social.employment_end_date IS '厚生年金資格喪失日';
COMMENT ON COLUMN prm_personal_social.fund_number IS '厚年基金加入者番号';
COMMENT ON COLUMN prm_personal_social.fund_start_date IS '厚年基金加入日';
COMMENT ON COLUMN prm_personal_social.fund_end_date IS '厚年基金脱退日';
COMMENT ON COLUMN prm_personal_social.fixed_health IS '定額健康保険料';
COMMENT ON COLUMN prm_personal_social.fixed_care IS '定額介護保険料';
COMMENT ON COLUMN prm_personal_social.fixed_employment IS '定額厚生年金';
COMMENT ON COLUMN prm_personal_social.fixed_fund IS '定額厚生年金基金';
COMMENT ON COLUMN prm_personal_social.fixed_owner_health IS '定額事業主健康保険料';
COMMENT ON COLUMN prm_personal_social.fixed_owner_care IS '定額事業主介護保険料';
COMMENT ON COLUMN prm_personal_social.fixed_owner_employment IS '定額事業主厚生年金';
COMMENT ON COLUMN prm_personal_social.fixed_owner_fund IS '定額事業主厚生年金基金';
COMMENT ON COLUMN prm_personal_social.fixed_owner_child IS '定額事業主児童手当';
COMMENT ON COLUMN prm_personal_social.employment_acquire_date is '厚生年金取得処理済年月日';
COMMENT ON COLUMN prm_personal_social.employment_lost_date is '厚生年金喪失処理済年月日';
COMMENT ON COLUMN prm_personal_social.employment_diary_distribute_date is '厚生年金手帳配布日';
COMMENT ON COLUMN prm_personal_social.employment_diary_collect_date is '厚生年金手帳回収日';
COMMENT ON COLUMN prm_personal_social.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_social.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_social.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_social.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_social.update_user IS '更新者';


CREATE TABLE prm_personal_transfer
(
  prm_personal_transfer_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  order_number integer NOT NULL DEFAULT 0, -- 連番
  payment_type integer NOT NULL DEFAULT 0, -- 支払区分
  exchange_code character varying(2) DEFAULT ''::character varying, -- 仕向コード
  suffer_bank character varying(4) DEFAULT ''::character varying, -- 被仕向銀行
  suffer_branch character varying(3) DEFAULT ''::character varying, -- 被仕向支店
  deposit_type integer NOT NULL DEFAULT 1, -- 預金種別
  account_number character varying(7) DEFAULT ''::character varying, -- 口座番号
  account_name character varying(16) DEFAULT ''::character varying, -- 口座名義漢字
  account_kana character varying(30) DEFAULT ''::character varying, -- 口座名義ｶﾅ
  account_fixed integer NOT NULL DEFAULT 0, -- 固定額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_transfer_pkey PRIMARY KEY (prm_personal_transfer_id )
)
;
COMMENT ON TABLE prm_personal_transfer IS '個人振込銀行情報';
COMMENT ON COLUMN prm_personal_transfer.prm_personal_transfer_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_transfer.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_transfer.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_personal_transfer.order_number IS '連番';
COMMENT ON COLUMN prm_personal_transfer.payment_type IS '支払区分';
COMMENT ON COLUMN prm_personal_transfer.exchange_code IS '仕向コード';
COMMENT ON COLUMN prm_personal_transfer.suffer_bank IS '被仕向銀行';
COMMENT ON COLUMN prm_personal_transfer.suffer_branch IS '被仕向支店';
COMMENT ON COLUMN prm_personal_transfer.deposit_type IS '預金種別';
COMMENT ON COLUMN prm_personal_transfer.account_number IS '口座番号';
COMMENT ON COLUMN prm_personal_transfer.account_name IS '口座名義漢字';
COMMENT ON COLUMN prm_personal_transfer.account_kana IS '口座名義ｶﾅ';
COMMENT ON COLUMN prm_personal_transfer.account_fixed IS '固定額';
COMMENT ON COLUMN prm_personal_transfer.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_transfer.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_transfer.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_transfer.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_transfer.update_user IS '更新者';


CREATE TABLE prm_personal_unit
(
  prm_personal_unit_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying, -- 個人ID
  diem_unit_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 日給単価コード
  diem_unit_price double precision NOT NULL DEFAULT 0, -- 日給単価
  hourly_unit_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 時給単価コード
  hourly_unit_price double precision NOT NULL DEFAULT 0, -- 時給単価
  overtime_basic_unit double precision NOT NULL DEFAULT 0, -- 残業基準単価
  overtime_unit double precision NOT NULL DEFAULT 0, -- 残業単価
  latetime_unit double precision NOT NULL DEFAULT 0, -- 深夜残業単価
  legal_overtime_unit double precision NOT NULL DEFAULT 0, -- 法定休日残業単価
  legal_latetime_unit double precision NOT NULL DEFAULT 0, -- 法定休日深夜残業単価
  specific_overtime_unit double precision NOT NULL DEFAULT 0, -- 所定休日残業単価
  specific_latetime_unit double precision NOT NULL DEFAULT 0, -- 所定休日深夜残業単価
  hour_45_over_unit double precision NOT NULL DEFAULT 0, -- ４５時間越割増単価
  hour_60_over_unit double precision NOT NULL DEFAULT 0, -- ６０時間越割増単価
  late_early_basic_unit double precision NOT NULL DEFAULT 0, -- 遅早等控除基準単価
  late_early_unit double precision NOT NULL DEFAULT 0, -- 遅早等控除単価
  absence_basic_unit double precision NOT NULL DEFAULT 0, -- 欠勤控除基準単価
  absence_unit double precision NOT NULL DEFAULT 0, -- 欠勤控除単価
  unit_code_1 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価1コード
  unit_price_1 double precision NOT NULL DEFAULT 0, -- 単価1金額
  unit_code_2 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価2コード
  unit_price_2 double precision NOT NULL DEFAULT 0, -- 単価2金額
  unit_code_3 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価3コード
  unit_price_3 double precision NOT NULL DEFAULT 0, -- 単価3金額
  unit_code_4 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価4コード
  unit_price_4 double precision NOT NULL DEFAULT 0, -- 単価4金額
  unit_code_5 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価5コード
  unit_price_5 double precision NOT NULL DEFAULT 0, -- 単価5金額
  unit_code_6 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価6コード
  unit_price_6 double precision NOT NULL DEFAULT 0, -- 単価6金額
  unit_code_7 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価7コード
  unit_price_7 double precision NOT NULL DEFAULT 0, -- 単価7金額
  unit_code_8 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価8コード
  unit_price_8 double precision NOT NULL DEFAULT 0, -- 単価8金額
  unit_code_9 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価9コード
  unit_price_9 double precision NOT NULL DEFAULT 0, -- 単価9金額
  unit_code_10 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価10コード
  unit_price_10 double precision NOT NULL DEFAULT 0, -- 単価10金額
  unit_code_11 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価11コード
  unit_price_11 double precision NOT NULL DEFAULT 0, -- 単価11金額
  unit_code_12 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価12コード
  unit_price_12 double precision NOT NULL DEFAULT 0, -- 単価12金額
  unit_code_13 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価13コード
  unit_price_13 double precision NOT NULL DEFAULT 0, -- 単価13金額
  unit_code_14 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価14コード
  unit_price_14 double precision NOT NULL DEFAULT 0, -- 単価14金額
  unit_code_15 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価15コード
  unit_price_15 double precision NOT NULL DEFAULT 0, -- 単価15金額
  unit_code_16 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価16コード
  unit_price_16 double precision NOT NULL DEFAULT 0, -- 単価16金額
  unit_code_17 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価17コード
  unit_price_17 double precision NOT NULL DEFAULT 0, -- 単価17金額
  unit_code_18 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価18コード
  unit_price_18 double precision NOT NULL DEFAULT 0, -- 単価18金額
  unit_code_19 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価19コード
  unit_price_19 double precision NOT NULL DEFAULT 0, -- 単価19金額
  unit_code_20 character varying(10) NOT NULL DEFAULT ''::character varying, -- 単価20コード
  unit_price_20 double precision NOT NULL DEFAULT 0, -- 単価20金額
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_personal_unit_pkey PRIMARY KEY (prm_personal_unit_id )
)
;
COMMENT ON TABLE prm_personal_unit IS '個人単価情報';
COMMENT ON COLUMN prm_personal_unit.prm_personal_unit_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_personal_unit.personal_id IS '個人ID';
COMMENT ON COLUMN prm_personal_unit.diem_unit_code IS '日給単価コード';
COMMENT ON COLUMN prm_personal_unit.diem_unit_price IS '日給単価';
COMMENT ON COLUMN prm_personal_unit.hourly_unit_code IS '時給単価コード';
COMMENT ON COLUMN prm_personal_unit.hourly_unit_price IS '時給単価';
COMMENT ON COLUMN prm_personal_unit.overtime_basic_unit IS '残業基準単価';
COMMENT ON COLUMN prm_personal_unit.overtime_unit IS '残業単価';
COMMENT ON COLUMN prm_personal_unit.latetime_unit IS '深夜残業単価';
COMMENT ON COLUMN prm_personal_unit.legal_overtime_unit IS '法定休日残業単価';
COMMENT ON COLUMN prm_personal_unit.legal_latetime_unit IS '法定休日深夜残業単価';
COMMENT ON COLUMN prm_personal_unit.specific_overtime_unit IS '所定休日残業単価';
COMMENT ON COLUMN prm_personal_unit.specific_latetime_unit IS '所定休日深夜残業単価';
COMMENT ON COLUMN prm_personal_unit.hour_45_over_unit IS '４５時間越割増単価';
COMMENT ON COLUMN prm_personal_unit.hour_60_over_unit IS '６０時間越割増単価';
COMMENT ON COLUMN prm_personal_unit.late_early_basic_unit IS '遅早等控除基準単価';
COMMENT ON COLUMN prm_personal_unit.late_early_unit IS '遅早等控除単価';
COMMENT ON COLUMN prm_personal_unit.absence_basic_unit IS '欠勤控除基準単価';
COMMENT ON COLUMN prm_personal_unit.absence_unit IS '欠勤控除単価';
COMMENT ON COLUMN prm_personal_unit.unit_code_1 IS '単価1コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_1 IS '単価1金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_2 IS '単価2コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_2 IS '単価2金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_3 IS '単価3コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_3 IS '単価3金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_4 IS '単価4コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_4 IS '単価4金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_5 IS '単価5コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_5 IS '単価5金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_6 IS '単価6コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_6 IS '単価6金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_7 IS '単価7コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_7 IS '単価7金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_8 IS '単価8コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_8 IS '単価8金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_9 IS '単価9コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_9 IS '単価9金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_10 IS '単価10コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_10 IS '単価10金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_11 IS '単価11コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_11 IS '単価11金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_12 IS '単価12コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_12 IS '単価12金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_13 IS '単価13コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_13 IS '単価13金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_14 IS '単価14コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_14 IS '単価14金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_15 IS '単価15コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_15 IS '単価15金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_16 IS '単価16コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_16 IS '単価16金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_17 IS '単価17コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_17 IS '単価17金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_18 IS '単価18コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_18 IS '単価18金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_19 IS '単価19コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_19 IS '単価19金額';
COMMENT ON COLUMN prm_personal_unit.unit_code_20 IS '単価20コード';
COMMENT ON COLUMN prm_personal_unit.unit_price_20 IS '単価20金額';
COMMENT ON COLUMN prm_personal_unit.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_personal_unit.insert_date IS '登録日';
COMMENT ON COLUMN prm_personal_unit.insert_user IS '登録者';
COMMENT ON COLUMN prm_personal_unit.update_date IS '更新日';
COMMENT ON COLUMN prm_personal_unit.update_user IS '更新者';


CREATE TABLE prm_resident_tax
(
  prm_resident_tax_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  activate_date date NOT NULL, -- 有効日
  local_public_code character varying(6) NOT NULL DEFAULT ''::character varying, -- 市区町村コード
  city_name character varying(32) NOT NULL DEFAULT ''::character varying, -- 市区町村名
  city_kana character varying(15) NOT NULL DEFAULT ''::character varying, -- 市区町村名ｶﾅ
  specific_number character varying(15) NOT NULL DEFAULT ''::character varying, -- 指定番号
  compile_code character varying(6) NOT NULL DEFAULT ''::character varying, -- とりまとめ先コードﾞ
  giro_account_number character varying(8) NOT NULL DEFAULT ''::character varying, -- 郵便振替口座番号
  inactivate_flag integer NOT NULL DEFAULT 0, -- 無効フラグ
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_resident_tax_pkey PRIMARY KEY (prm_resident_tax_id )
)
;
COMMENT ON TABLE prm_resident_tax IS '住民税納付先マスタ';
COMMENT ON COLUMN prm_resident_tax.prm_resident_tax_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_resident_tax.company_code IS '会社コード';
COMMENT ON COLUMN prm_resident_tax.activate_date IS '有効日';
COMMENT ON COLUMN prm_resident_tax.local_public_code IS '市区町村コード';
COMMENT ON COLUMN prm_resident_tax.city_name IS '市区町村名';
COMMENT ON COLUMN prm_resident_tax.city_kana IS '市区町村名ｶﾅ';
COMMENT ON COLUMN prm_resident_tax.specific_number IS '指定番号';
COMMENT ON COLUMN prm_resident_tax.compile_code IS 'とりまとめ先コードﾞ';
COMMENT ON COLUMN prm_resident_tax.giro_account_number IS '郵便振替口座番号';
COMMENT ON COLUMN prm_resident_tax.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN prm_resident_tax.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_resident_tax.insert_date IS '登録日';
COMMENT ON COLUMN prm_resident_tax.insert_user IS '登録者';
COMMENT ON COLUMN prm_resident_tax.update_date IS '更新日';
COMMENT ON COLUMN prm_resident_tax.update_user IS '更新者';


CREATE TABLE prm_system_parameter
(
  prm_system_parameter_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT 1, -- 会社コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  item_number character varying(3) NOT NULL DEFAULT 0, -- 項目番号
  scr_parameter_master integer NOT NULL DEFAULT 0, -- パラメータ設定
  scr_payroll_system integer NOT NULL DEFAULT 0, -- 給与体系設定
  scr_detail_item integer NOT NULL DEFAULT 0, -- 支給明細設定(集計項目選択)
  scr_payroll_item integer NOT NULL DEFAULT 0, -- 賃金台帳設定(集計項目選択)
  scr_total_item integer NOT NULL DEFAULT 0, -- 累計設定(集計項目選択)
  scr_calc_correction integer NOT NULL DEFAULT 0, -- 給与データ訂正照会一覧
  scr_detail_correction integer NOT NULL DEFAULT 0, -- 給与明細登録／照会
  scr_payroll_scale integer NOT NULL DEFAULT 0, -- 賃金表設定
  scr_formula_master integer NOT NULL DEFAULT 0, -- 計算式設定
  scr_calc_item integer NOT NULL DEFAULT 0, -- 勤怠支給控除登録
  scr_unit_price integer NOT NULL DEFAULT 0, -- 単価情報設定
  scr_allowance_payment integer NOT NULL DEFAULT 0, -- 固定支給手当情報登録
  scr_allowance_deduction integer NOT NULL DEFAULT 0, -- 固定控除情報登録
  csv_item_type integer NOT NULL DEFAULT 0, -- CSV項目区分
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_system_parameter_pkey PRIMARY KEY (prm_system_parameter_id )
)
;
COMMENT ON TABLE prm_system_parameter IS 'パラメータシステム設定マスタ';
COMMENT ON COLUMN prm_system_parameter.prm_system_parameter_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_system_parameter.company_code IS '会社コード';
COMMENT ON COLUMN prm_system_parameter.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_system_parameter.item_number IS '項目番号';
COMMENT ON COLUMN prm_system_parameter.scr_parameter_master IS 'パラメータ設定';
COMMENT ON COLUMN prm_system_parameter.scr_payroll_system IS '給与体系設定';
COMMENT ON COLUMN prm_system_parameter.scr_detail_item IS '支給明細設定(集計項目選択)';
COMMENT ON COLUMN prm_system_parameter.scr_payroll_item IS '賃金台帳設定(集計項目選択)';
COMMENT ON COLUMN prm_system_parameter.scr_total_item IS '累計設定(集計項目選択)';
COMMENT ON COLUMN prm_system_parameter.scr_calc_correction IS '給与データ訂正照会一覧';
COMMENT ON COLUMN prm_system_parameter.scr_detail_correction IS '給与明細登録／照会';
COMMENT ON COLUMN prm_system_parameter.scr_payroll_scale IS '賃金表設定';
COMMENT ON COLUMN prm_system_parameter.scr_formula_master IS '計算式設定';
COMMENT ON COLUMN prm_system_parameter.scr_calc_item IS '勤怠支給控除登録';
COMMENT ON COLUMN prm_system_parameter.scr_unit_price IS '単価情報設定';
COMMENT ON COLUMN prm_system_parameter.scr_allowance_payment IS '固定支給手当情報登録';
COMMENT ON COLUMN prm_system_parameter.scr_allowance_deduction IS '固定控除情報登録';
COMMENT ON COLUMN prm_system_parameter.csv_item_type IS 'CSV項目区分';
COMMENT ON COLUMN prm_system_parameter.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_system_parameter.insert_date IS '登録日';
COMMENT ON COLUMN prm_system_parameter.insert_user IS '登録者';
COMMENT ON COLUMN prm_system_parameter.update_date IS '更新日';
COMMENT ON COLUMN prm_system_parameter.update_user IS '更新者';


CREATE TABLE prm_system_parameter_item
(
  prm_system_parameter_item_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT ''::character varying, -- 会社コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  item_number character varying(3) NOT NULL DEFAULT ''::character varying, -- 項目番号
  use_type integer NOT NULL DEFAULT 0, -- 使用区分
  basic_item_name character varying(20) NOT NULL DEFAULT ''::character varying, -- 基本項目名
  view_item_name character varying(12) NOT NULL DEFAULT ''::character varying, -- 表示項目名
  formula_type integer NOT NULL DEFAULT 0, -- 計算式区分
  formula_order integer NOT NULL DEFAULT 0, -- 計算順位
  calc_withholding_type integer NOT NULL DEFAULT 0, -- 集計区分源泉
  calc_reward_type integer NOT NULL DEFAULT 0, -- 集計区分報酬
  calc_employment_type integer NOT NULL DEFAULT 0, -- 集計区分雇用保険
  calc_wage_type integer NOT NULL DEFAULT 0, -- 集計区分賃金
  calc_advance_type integer NOT NULL DEFAULT 0, -- 集計区分立替金
  parameter_remark character varying(60) DEFAULT ''::character varying, -- 備考
  bonus_basic_type_monthly integer NOT NULL DEFAULT 0, -- 賞与基準額完全月給
  bonus_basic_type_daily_monthly integer NOT NULL DEFAULT 0, -- 賞与基準額日給月給
  overtime_monthly integer NOT NULL DEFAULT 0, -- 残業基準額完全月給
  overtime_daily_monthly integer NOT NULL DEFAULT 0, -- 残業基準額日給月給
  overtime_daily integer NOT NULL DEFAULT 0, -- 残業基準額日給
  overtime_hourly integer NOT NULL DEFAULT 0, -- 残業基準額時給
  lateearly_monthly integer NOT NULL DEFAULT 0, -- 遅早控除基準額完全月給
  lateearly_daily_monthly integer NOT NULL DEFAULT 0, -- 遅早控除基準額日給月給
  lateearly_daily integer NOT NULL DEFAULT 0, -- 遅早控除基準額日給
  absence_monthly integer NOT NULL DEFAULT 0, -- 欠勤控除基準額完全月給
  absence_daily_monthly integer NOT NULL DEFAULT 0, -- 欠勤控除基準額日給月給
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_system_parameter_item_pkey PRIMARY KEY (prm_system_parameter_item_id )
)
;
COMMENT ON TABLE prm_system_parameter_item IS 'パラメータシステム設定項目マスタ';
COMMENT ON COLUMN prm_system_parameter_item.prm_system_parameter_item_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_system_parameter_item.company_code IS '会社コード';
COMMENT ON COLUMN prm_system_parameter_item.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_system_parameter_item.item_number IS '項目番号';
COMMENT ON COLUMN prm_system_parameter_item.use_type IS '使用区分';
COMMENT ON COLUMN prm_system_parameter_item.basic_item_name IS '基本項目名';
COMMENT ON COLUMN prm_system_parameter_item.view_item_name IS '表示項目名';
COMMENT ON COLUMN prm_system_parameter_item.formula_type IS '計算式区分';
COMMENT ON COLUMN prm_system_parameter_item.formula_order IS '計算順位';
COMMENT ON COLUMN prm_system_parameter_item.calc_withholding_type IS '集計区分源泉';
COMMENT ON COLUMN prm_system_parameter_item.calc_reward_type IS '集計区分報酬';
COMMENT ON COLUMN prm_system_parameter_item.calc_employment_type IS '集計区分雇用保険';
COMMENT ON COLUMN prm_system_parameter_item.calc_wage_type IS '集計区分賃金';
COMMENT ON COLUMN prm_system_parameter_item.calc_advance_type IS '集計区分立替金';
COMMENT ON COLUMN prm_system_parameter_item.parameter_remark IS '備考';
COMMENT ON COLUMN prm_system_parameter_item.bonus_basic_type_monthly IS '賞与基準額完全月給';
COMMENT ON COLUMN prm_system_parameter_item.bonus_basic_type_daily_monthly IS '賞与基準額日給月給';
COMMENT ON COLUMN prm_system_parameter_item.overtime_monthly IS '残業基準額完全月給';
COMMENT ON COLUMN prm_system_parameter_item.overtime_daily_monthly IS '残業基準額日給月給';
COMMENT ON COLUMN prm_system_parameter_item.overtime_daily IS '残業基準額日給';
COMMENT ON COLUMN prm_system_parameter_item.overtime_hourly IS '残業基準額時給';
COMMENT ON COLUMN prm_system_parameter_item.lateearly_monthly IS '遅早控除基準額完全月給';
COMMENT ON COLUMN prm_system_parameter_item.lateearly_daily_monthly IS '遅早控除基準額日給月給';
COMMENT ON COLUMN prm_system_parameter_item.lateearly_daily IS '遅早控除基準額日給';
COMMENT ON COLUMN prm_system_parameter_item.absence_monthly IS '欠勤控除基準額完全月給';
COMMENT ON COLUMN prm_system_parameter_item.absence_daily_monthly IS '欠勤控除基準額日給月給';
COMMENT ON COLUMN prm_system_parameter_item.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_system_parameter_item.insert_date IS '登録日';
COMMENT ON COLUMN prm_system_parameter_item.insert_user IS '登録者';
COMMENT ON COLUMN prm_system_parameter_item.update_date IS '更新日';
COMMENT ON COLUMN prm_system_parameter_item.update_user IS '更新者';


CREATE TABLE prm_system_parameter_type
(
  prm_system_parameter_type_id bigint NOT NULL DEFAULT 0, -- レコード識別ID
  company_code character varying(10) NOT NULL DEFAULT 1, -- 会社コード
  payroll_or_bonus integer NOT NULL DEFAULT 1, -- 業務区分
  type_code character varying(50) NOT NULL DEFAULT ''::character varying, -- 種類コード
  item_number character varying(3) NOT NULL DEFAULT 0, -- 項目番号
  type_value integer NOT NULL DEFAULT 0, -- 値
  delete_flag integer NOT NULL DEFAULT 0, -- 削除フラグ
  insert_date timestamp without time zone NOT NULL, -- 登録日
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 登録者
  update_date timestamp without time zone NOT NULL, -- 更新日
  update_user character varying(50) NOT NULL DEFAULT ''::character varying, -- 更新者
  CONSTRAINT prm_system_parameter_type_pkey PRIMARY KEY (prm_system_parameter_type_id )
)
;
COMMENT ON TABLE prm_system_parameter_type IS 'パラメータシステム設定種類マスタ';
COMMENT ON COLUMN prm_system_parameter_type.prm_system_parameter_type_id IS 'レコード識別ID';
COMMENT ON COLUMN prm_system_parameter_type.company_code IS '会社コード';
COMMENT ON COLUMN prm_system_parameter_type.payroll_or_bonus IS '業務区分';
COMMENT ON COLUMN prm_system_parameter_type.type_code IS '種類コード';
COMMENT ON COLUMN prm_system_parameter_type.item_number IS '項目番号';
COMMENT ON COLUMN prm_system_parameter_type.type_value IS '値';
COMMENT ON COLUMN prm_system_parameter_type.delete_flag IS '削除フラグ';
COMMENT ON COLUMN prm_system_parameter_type.insert_date IS '登録日';
COMMENT ON COLUMN prm_system_parameter_type.insert_user IS '登録者';
COMMENT ON COLUMN prm_system_parameter_type.update_date IS '更新日';
COMMENT ON COLUMN prm_system_parameter_type.update_user IS '更新者';


CREATE SEQUENCE prd_adjustment_calc_id_seq;
CREATE SEQUENCE prd_adjustment_dependent_detail_id_seq;
CREATE SEQUENCE prm_adjustment_defined_id_seq;
CREATE SEQUENCE prd_adjustment_report_id_seq;
CREATE SEQUENCE prd_bonus_parameter_calc_id_seq;
CREATE SEQUENCE prd_employee_revision_id_seq;
CREATE SEQUENCE prd_execute_start_history_id_seq;
CREATE SEQUENCE prd_health_revision_id_seq;
CREATE SEQUENCE prd_payroll_bonus_calc_id_seq;
CREATE SEQUENCE prd_payroll_bonus_detail_id_seq;
CREATE SEQUENCE prd_payroll_bonus_transfer_id_seq;
CREATE SEQUENCE prd_payroll_change_calc_id_seq;
CREATE SEQUENCE prd_payroll_parameter_calc_id_seq;
CREATE SEQUENCE prd_resident_tax_transfer_id_seq;
CREATE SEQUENCE prm_adjustment_income_deduction_id_seq;
CREATE SEQUENCE prm_adjustment_income_difference_id_seq;
CREATE SEQUENCE prm_adjustment_insurance_deduction_id_seq;
CREATE SEQUENCE prm_adjustment_request_id_seq;
CREATE SEQUENCE prm_adjustment_tax_id_seq;
CREATE SEQUENCE prm_bank_id_seq;
CREATE SEQUENCE prm_bonus_k_tax_id_seq;
CREATE SEQUENCE prm_bonus_o_tax_id_seq;
CREATE SEQUENCE prm_company_id_seq;
CREATE SEQUENCE prm_company_payroll_id_seq;
CREATE SEQUENCE prm_detail_id_seq;
CREATE SEQUENCE prm_detail_item_id_seq;
CREATE SEQUENCE prm_employee_pension_grade_id_seq;
CREATE SEQUENCE prm_exchange_bank_id_seq;
CREATE SEQUENCE prm_health_insurance_grade_id_seq;
CREATE SEQUENCE prm_health_office_id_seq;
CREATE SEQUENCE prm_labor_management_id_seq;
CREATE SEQUENCE prm_labor_office_id_seq;
CREATE SEQUENCE prm_message_id_seq;
CREATE SEQUENCE prm_name_management_id_seq;
CREATE SEQUENCE prm_naming_id_seq;
CREATE SEQUENCE prm_office_id_seq;
CREATE SEQUENCE prm_payroll_book_id_seq;
CREATE SEQUENCE prm_payroll_human_id_seq;
CREATE SEQUENCE prm_payroll_income_deduction_id_seq;
CREATE SEQUENCE prm_payroll_k_income_deduction_id_seq;
CREATE SEQUENCE prm_payroll_o_tax_id_seq;
CREATE SEQUENCE prm_payroll_scale_id_seq;
CREATE SEQUENCE prm_payroll_section_id_seq;
CREATE SEQUENCE prm_payroll_system_id_seq;
CREATE SEQUENCE prm_payroll_system_item_id_seq;
CREATE SEQUENCE prm_payroll_tax_id_seq;
CREATE SEQUENCE prm_payroll_type_rate_id_seq;
CREATE SEQUENCE prm_personal_address_id_seq;
CREATE SEQUENCE prm_personal_dependent_id_seq;
CREATE SEQUENCE prm_personal_dependent_detail_id_seq;
CREATE SEQUENCE prm_personal_fixed_deduction_id_seq;
CREATE SEQUENCE prm_personal_fixed_payment_id_seq;
CREATE SEQUENCE prm_personal_labor_id_seq;
CREATE SEQUENCE prm_personal_payroll_id_seq;
CREATE SEQUENCE prm_personal_resident_id_seq;
CREATE SEQUENCE prm_personal_social_id_seq;
CREATE SEQUENCE prm_personal_transfer_id_seq;
CREATE SEQUENCE prm_personal_unit_id_seq;
CREATE SEQUENCE prm_resident_tax_id_seq;
CREATE SEQUENCE prm_system_parameter_id_seq;
CREATE SEQUENCE prm_system_parameter_item_id_seq;
CREATE SEQUENCE prm_system_parameter_type_id_seq;


CREATE INDEX prm_personal_payroll_index1 ON prm_personal_payroll(personal_id,delete_flag);
CREATE INDEX prm_personal_social_index1 ON prm_personal_social(delete_flag,personal_id);
CREATE INDEX prm_personal_labor_index1 ON prm_personal_labor(delete_flag,personal_id);
CREATE INDEX prm_personal_dependent_index1 ON prm_personal_dependent(delete_flag,personal_id);
CREATE INDEX prm_personal_address_index1 ON prm_personal_address(delete_flag,personal_id);
CREATE INDEX prm_personal_transfer_index1 ON prm_personal_transfer(personal_id, payroll_or_bonus, order_number, delete_flag);
CREATE INDEX prm_payroll_human_index1 ON prm_payroll_human(personal_id,delete_flag);
CREATE INDEX prm_company_payroll_index1 ON prm_company_payroll(delete_flag,company_code);
CREATE INDEX prd_payroll_parameter_calc_index1 ON prd_payroll_parameter_calc(personal_id,delete_flag,execute_date,execute_times);
CREATE INDEX prd_payroll_bonus_detail_index1 ON prd_payroll_bonus_detail(delete_flag,payroll_or_bonus,execute_date,execute_times,personal_id);
CREATE INDEX prd_payroll_bonus_calc_index1 ON prd_payroll_bonus_calc(personal_id,delete_flag,payroll_or_bonus,execute_date);
CREATE INDEX prd_payroll_change_calc_index1 ON prd_payroll_change_calc(personal_id,delete_flag,inactivate_flag);
CREATE INDEX prd_health_revision_index1 ON prd_health_revision(delete_flag,personal_id);
CREATE INDEX prd_employee_revision_index1 ON prd_employee_revision( delete_flag, personal_id);
CREATE INDEX prd_adjustment_calc_index1 ON prd_adjustment_calc (delete_flag,inactivate_flag,personal_id,adjustment_fiscal_year);
CREATE INDEX prd_adjustment_report_index1 ON prd_adjustment_report (delete_flag,personal_id);
CREATE INDEX prd_bonus_parameter_calc_index1 ON prd_bonus_parameter_calc(personal_id, delete_flag, execute_date, execute_times);

INSERT INTO prm_adjustment_income_deduction(
	prm_adjustment_income_deduction_id, 
	above, 
	rate, 
	deduction_price, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_adjustment_income_deduction_id_seq'), '0', '0', '0', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '651000', '100', '650000', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '1619000', '60', '2400', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '1620000', '60', '2000', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '1622000', '60', '1200', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '1624000', '60', '400', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '1628000', '60', '0', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '1800000', '70', '180000', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '3600000', '80', '540000', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '6600000', '90', '1200000', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_income_deduction_id_seq'), '10000000', '100', '2200000', '0', current_timestamp, 'system', current_timestamp, 'system')
;


INSERT INTO prm_adjustment_income_difference(
	prm_adjustment_income_difference_id, 
	above, 
	dire, 
	min_value, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_adjustment_income_difference_id_seq'), 0, 1, 0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_income_difference_id_seq'), 1619000, 1000, 1619000, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_income_difference_id_seq'), 1620000, 2000, 1620000, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_income_difference_id_seq'), 1624000, 4000, 1624000, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_income_difference_id_seq'), 6600000, 1, 6600000, 0, now(), 'system', now(), 'system')
;

INSERT INTO prm_adjustment_insurance_deduction(
	prm_adjustment_insurance_deduction_id, 
	insurance_type, 
	above, 
	rate_numerator, 
	rate_denominator, 
	add_price, 
	limit_deduction, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_adjustment_insurance_deduction_id_seq'), '111',      1, 1, 1,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '111',  25001, 1, 2,  12500,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '111',  50001, 1, 4,  25000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '111', 100000, 0, 1,  50000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '112',      1, 1, 1,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '112',  20001, 1, 2,  10000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '112',  40001, 1, 4,  20000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '112',  80000, 0, 1,  40000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '121',      1, 1, 1,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '121',  25001, 1, 2,  12500,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '121',  50001, 1, 4,  25000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '121', 100000, 0, 1,  50000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '122',      1, 1, 1,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '122',  20001, 1, 2,  10000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '122',  40001, 1, 4,  20000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '122',  80000, 0, 1,  40000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '131',      1, 1, 1,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '131',  20001, 1, 2,  10000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '131',  40001, 1, 4,  20000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '131',  80000, 0, 1,  40000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '191',      0, 0, 0,      0,  40000, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '192',      0, 0, 0,      0, 120000, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '211',      0, 1, 1,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '211',  50001, 0, 1,  50000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '221',      0, 1, 1,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '221',  10001, 1, 2,   5000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '221',  20001, 0, 1,  15000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '291',      0, 0, 0,      0,  50000, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910',      0, 0, 0,      0,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 380001, 0, 0, 380000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 400000, 0, 0, 360000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 450000, 0, 0, 310000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 500000, 0, 0, 260000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 550000, 0, 0, 210000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 600000, 0, 0, 160000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 650000, 0, 0, 110000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 700000, 0, 0,  60000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 750000, 0, 0,  30000,      0, 0, now(), 'system', now(), 'system')
,(nextval('prm_adjustment_insurance_deduction_id_seq'), '910', 760000, 0, 0,      0,      0, 0, now(), 'system', now(), 'system')
;


INSERT INTO prm_adjustment_request(
	prm_adjustment_request_id, 
	adjustment_total_income, 
	adjustment_other_income, 
	widow_total_income, 
	widower_total_income, 
	special_widow_total_income, 
	labor_student_total_income, 
	labor_student_other_income, 
	special_terms_total_income, 
	basic_dependent_deduction, 
	together_special_desability_deduction, 
	special_disability_deduction, 
	desability_deduction, 
	special_widow_deduction, 
	widow_deduction, 
	widower_deduction, 
	labor_student_deduction, 
	together_age_deduction, 
	specific_dependent_deduction, 
	age_dependent_deduction, 
	age_spouse_deduction, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (
 	nextval('prm_adjustment_request_id_seq'), 
 	20000000, 
 	200000, 
 	5000000, 
 	5000000, 
 	5000000, 
 	650000, 
 	100000, 
 	10000000, 
 	380000, 
 	750000, 
 	400000, 
 	270000, 
 	350000, 
 	270000, 
 	270000, 
 	270000, 
 	200000, 
 	250000, 
 	100000, 
 	100000, 
 	0, now(), 'system', now(), 'system')
;


INSERT INTO prm_adjustment_tax(
	prm_adjustment_tax_id, 
	above, 
	tax_rate, 
	deduction_price, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_adjustment_tax_id_seq'), '0', '5', '0', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_tax_id_seq'), '1950001', '10', '97500', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_tax_id_seq'), '3300001', '20', '427500', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_tax_id_seq'), '6950001', '23', '636000', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_tax_id_seq'), '9000001', '33', '1536000', '0', current_timestamp, 'system', current_timestamp, 'system')
,(nextval('prm_adjustment_tax_id_seq'), '17320001', '0', '0', '0', current_timestamp, 'system', current_timestamp, 'system')
;



INSERT INTO prm_bonus_k_tax(
	prm_bonus_k_tax_id, 
	execute_date, 
	depnendent_total, 
	above, 
	tax_rate, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,68000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,79000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,252000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,300000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,334000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,363000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,395000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,426000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,550000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,647000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,699000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,730000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,764000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,804000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,857000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,926000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,1321000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,1532000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,2661000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',0,3548000,45.945,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,94000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,243000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,282000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,338000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,365000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,394000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,422000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,455000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,550000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,663000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,720000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,752000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,787000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,826000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,885000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,956000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,1346000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,1560000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,2685000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',1,3580000,45.945,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,133000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,269000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,312000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,369000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,393000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,420000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,450000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,484000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,550000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,678000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,741000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,774000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,810000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,852000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,914000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,987000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,1370000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,1589000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,2708000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',2,3611000,45.945,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,171000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,295000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,345000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,398000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,417000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,445000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,477000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,513000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,557000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,693000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,762000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,796000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,833000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,879000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,942000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,1017000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,1394000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,1617000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,2732000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',3,3643000,45.945,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,210000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,300000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,378000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,424000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,444000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,470000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,504000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,543000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,591000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,708000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,783000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,818000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,859000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,906000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,970000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,1048000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,1419000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,1645000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,2756000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',4,3675000,45.945,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,243000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,300000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,406000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,450000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,472000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,496000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,531000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,574000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,618000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,723000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,804000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,841000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,885000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,934000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,998000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,1078000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,1443000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,1674000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,2780000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',5,3706000,45.945,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,275000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,333000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,431000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,476000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,499000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,525000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,559000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,602000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,645000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,739000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,825000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,865000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,911000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,961000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,1026000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,1108000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,1468000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,1702000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,2803000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',6,3738000,45.945,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,0,0,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,308000,2.042,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,372000,4.084,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,456000,6.126,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,502000,8.168,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,527000,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,553000,12.252,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,588000,14.294,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,627000,16.336,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,671000,18.378,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,754000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,848000,22.462,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,890000,24.504,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,937000,26.546,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,988000,28.588,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,1054000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,1139000,32.672,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,1492000,35.735,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,1730000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,2827000,41.861,0,now(),'system',now(),'system')
,(nextval('prm_bonus_k_tax_id_seq'),'2017-01-01',7,3770000,45.945,0,now(),'system',now(),'system')
;


INSERT INTO prm_bonus_o_tax(
	prm_bonus_o_tax_id, 
	execute_date, 
	above, 
	tax_rate, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_bonus_o_tax_id_seq'),'2017-01-01',0,10.21,0,now(),'system',now(),'system')
,(nextval('prm_bonus_o_tax_id_seq'),'2017-01-01',239000,20.42,0,now(),'system',now(),'system')
,(nextval('prm_bonus_o_tax_id_seq'),'2017-01-01',296000,30.63,0,now(),'system',now(),'system')
,(nextval('prm_bonus_o_tax_id_seq'),'2017-01-01',528000,38.798,0,now(),'system',now(),'system')
,(nextval('prm_bonus_o_tax_id_seq'),'2017-01-01',1135000,45.945,0,now(),'system',now(),'system')
;


INSERT INTO prm_company(
	prm_company_id, 
	company_code, 
	activate_date, 
	company_name, 
	represent_full_name, 
	represent_title, 
	corporate_number, 
	postal_code_1, 
	postal_code_2, 
	prefecture, 
	address_1, 
	address_2, 
	address_3, 
	phone_number_1, 
	phone_number_2, 
	phone_number_3, 
	inactivate_flag, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (
 	nextval('prm_company_id_seq')
 	,'1'
 	,'2011-01-01'
 	,''
 	,''
 	,''
 	,''
 	,''
	,''
 	,''
 	,''
 	,''
 	,''
 	,''
 	,''
 	,''
 	,0
 	,0,now(),'system',now(),'system')
;


INSERT INTO prm_company_payroll(
	prm_company_payroll_id, 
	company_code, 
	activate_date, 
	start_date, 
	fund_type, 
	bill_use_type, 
	cash_fraction_unit, 
	health_collection_type, 
	resident_tax_type, 
	resident_exchange_code, 
	resident_tax_format_type, 
	resident_move_default, 
	resident_payment_limit, 
	resident_payment_month, 
	constant_name_1, 
	constant_value_1, 
	constant_name_2, 
	constant_value_2, 
	constant_name_3, 
	constant_value_3, 
	constant_name_4, 
	constant_value_4, 
	constant_name_5, 
	constant_value_5, 
	constant_name_6, 
	constant_value_6, 
	constant_name_7, 
	constant_value_7, 
	constant_name_8, 
	constant_value_8, 
	constant_name_9, 
	constant_value_9, 
	constant_name_10, 
	constant_value_10, 
	payroll_execute_date, 
	payroll_execute_times, 
	payroll_basic_date, 
	payroll_target_period_from, 
	payroll_target_period_to, 
	bonus_execute_date, 
	bonus_execute_times, 
	bonus_basic_date, 
	bonus_period, 
	bonus_target_period_from, 
	bonus_target_period_to, 
	payroll_bonus_last_execute, 
	adjustment_fiscal, 
	adjustment_calc_type, 
	adjustment_employee_code, 
	payroll_period_from, 
	payroll_times_from, 
	payroll_period_to, 
	payroll_times_to, 
	bonus_period_from, 
	bonus_times_from, 
	bonus_period_to, 
	bonus_times_to, 
	adjustment_type, 
	payroll_change_state, 
	payroll_change_start, 
	payroll_change_end, 
	payroll_change_basic, 
	payroll_change_type, 
	change_revision_fiscal, 
	change_minus_fiscal, 
	calc_execute_type, 
	calc_revision_fiscal, 
	calc_minus_fiscal, 
	health_lower_under, 
	health_upper_over, 
	employee_lower_under, 
	employee_upper_over, 
	data_save_limit, 
	inactivate_flag, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (
 	nextval('prm_company_payroll_id_seq')
 	,'1'
 	,'2011-01-01'
 	,null
 	,0
 	,0
 	,0
 	,0
 	,0
 	,''
 	,0
 	,''
 	,null
 	,null
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,''
 	,0
 	,'2001-01-01'
 	,1
 	,'2001-01-01'
 	,'2001-01-01'
 	,'2001-01-01'
 	,'2001-01-01'
 	,0
 	,'2001-01-01'
 	,1
 	,'2001-01-01'
 	,'2001-01-01'
 	,2012
 	,1
 	,1
 	,''
 	,'2001-01-01'
 	,1
 	,'2001-01-01'
 	,1
 	,'2001-01-01'
 	,1
 	,'2001-01-01'
 	,1
 	,1
 	,0
 	,'2001-01-01'
 	,'2001-01-01'
 	,'2001-01-01'
 	,1
 	,'2001-01-01'
 	,'2001-01-01'
 	,1
 	,'2001-01-01'
 	,'2001-01-01'
 	,53000
 	,1415000
 	,83000
 	,635000
 	,3
 	,0
 	,0,now(),'system',now(),'system')
;


INSERT INTO prm_employee_pension_grade(
	prm_employee_pension_grade_id, 
	above, 
	grade, 
	basic_reward, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_employee_pension_grade_id_seq'),0,1,88000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),93000,2,98000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),101000,3,104000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),107000,4,110000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),114000,5,118000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),122000,6,126000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),130000,7,134000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),138000,8,142000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),146000,9,150000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),155000,10,160000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),165000,11,170000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),175000,12,180000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),185000,13,190000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),195000,14,200000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),210000,15,220000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),230000,16,240000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),250000,17,260000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),270000,18,280000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),290000,19,300000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),310000,20,320000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),330000,21,340000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),350000,22,360000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),370000,23,380000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),395000,24,410000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),425000,25,440000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),455000,26,470000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),485000,27,500000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),515000,28,530000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),545000,29,560000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),575000,30,590000,0,now(),'system',now(),'system')
,(nextval('prm_employee_pension_grade_id_seq'),605000,31,620000,0,now(),'system',now(),'system')
;

INSERT INTO prm_health_insurance_grade(
	prm_health_insurance_grade_id, 
	above, 
	grade, 
	basic_reward, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_health_insurance_grade_id_seq'),0,1,58000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),63000,2,68000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),73000,3,78000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),83000,4,88000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),93000,5,98000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),101000,6,104000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),107000,7,110000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),114000,8,118000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),122000,9,126000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),130000,10,134000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),138000,11,142000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),146000,12,150000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),155000,13,160000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),165000,14,170000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),175000,15,180000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),185000,16,190000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),195000,17,200000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),210000,18,220000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),230000,19,240000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),250000,20,260000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),270000,21,280000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),290000,22,300000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),310000,23,320000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),330000,24,340000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),350000,25,360000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),370000,26,380000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),395000,27,410000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),425000,28,440000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),455000,29,470000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),485000,30,500000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),515000,31,530000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),545000,32,560000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),575000,33,590000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),605000,34,620000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),635000,35,650000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),665000,36,680000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),695000,37,710000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),730000,38,750000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),770000,39,790000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),810000,40,830000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),855000,41,880000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),905000,42,930000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),955000,43,980000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),1005000,44,1030000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),1055000,45,1090000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),1115000,46,1150000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),1175000,47,1210000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),1235000,48,1270000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),1295000,49,1330000,0,now(),'system',now(),'system')
,(nextval('prm_health_insurance_grade_id_seq'),1355000,50,1390000,0,now(),'system',now(),'system')
;

INSERT INTO prm_name_management(
	prm_name_management_id, 
	company_code, 
	naming_type, 
	naming_name, 
	naming_code_digit, 
	naming_name_digit, 
	remark_digit, 
	inactivate_flag, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_name_management_id_seq'),'1',501,'控除会社名',3,60,60,0,0,now(),'system',now(),'system')
,(nextval('prm_name_management_id_seq'),'1',502,'控除保険種別',3,60,60,0,0,now(),'system',now(),'system')
;


INSERT INTO prm_payroll_income_deduction(
	prm_payroll_income_deduction_id, 
	execute_date, 
	k_income_basic_deduction, 
	k_income_spouse_deduction, 
	k_income_dependent_deduction, 
	o_income_dependent_deduction, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_payroll_income_deduction_id_seq'),'2010-01-01',31667,31667,31667,1580,0,now(),'system',now(),'system')
,(nextval('prm_payroll_income_deduction_id_seq'),'2013-01-01',31667,31667,31667,1610,0,now(),'system',now(),'system')
;


INSERT INTO prm_payroll_k_income_deduction(
	prm_payroll_k_income_deduction_id, 
	execute_date, 
	above, 
	deduction_rate, 
	deduction_price, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_payroll_k_income_deduction_id_seq'),'2010-01-01',0,0,54167,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2010-01-01',135417,40,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2010-01-01',150000,30,15000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2010-01-01',300000,20,45000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2010-01-01',550000,10,100000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2010-01-01',833334,5,141667,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2013-01-01',0,0,54167,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2013-01-01',135417,40,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2013-01-01',150000,30,15000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2013-01-01',300000,20,45000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2013-01-01',550000,10,100000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2013-01-01',833334,5,141667,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2013-01-01',1250000,0,204167,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2016-01-01',0,0,54167,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2016-01-01',135417,40,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2016-01-01',150000,30,15000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2016-01-01',300000,20,45000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2016-01-01',550000,10,100000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2016-01-01',833334,5,141667,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2016-01-01',1000000,0,191667,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2017-01-01',0,0,54167,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2017-01-01',135417,40,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2017-01-01',150000,30,15000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2017-01-01',300000,20,45000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2017-01-01',550000,10,100000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_k_income_deduction_id_seq'),'2017-01-01',833334,0,183334,0,now(),'system',now(),'system')
;


INSERT INTO prm_payroll_o_tax(
	prm_payroll_o_tax_id, 
	execute_date, 
	above, 
	tax_rate, 
	tax_price, 
	excessive_price, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',0,3,0,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',88000,0,3100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',92000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',95000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',97000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',99000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',103000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',105000,0,3700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',109000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',111000,0,3900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',113000,0,4000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',117000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',119000,0,4200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',121000,0,4400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',123000,0,4700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',125000,0,5000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',127000,0,5300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',129000,0,5600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',131000,0,5900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',133000,0,6200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',135000,0,6500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',137000,0,6700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',139000,0,7000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',141000,0,7300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',143000,0,7600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',145000,0,7900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',147000,0,8200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',149000,0,8500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',151000,0,8800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',153000,0,9100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',155000,0,9400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',157000,0,9700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',159000,0,10000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',161000,0,10300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',163000,0,10600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',165000,0,10900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',167000,0,11200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',169000,0,11500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',171000,0,11800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',173000,0,12100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',175000,0,12400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',177000,0,12900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',179000,0,13600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',181000,0,14300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',183000,0,15000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',185000,0,15700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',187000,0,16400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',189000,0,17100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',191000,0,17700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',193000,0,18400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',195000,0,19100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',197000,0,19800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',199000,0,20500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',201000,0,21100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',203000,0,21700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',205000,0,22200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',207000,0,22800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',209000,0,23400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',211000,0,23900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',213000,0,24500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',215000,0,25000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',217000,0,25600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',219000,0,26200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',221000,0,26800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',224000,0,27800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',227000,0,28700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',230000,0,29700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',233000,0,30700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',236000,0,31700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',239000,0,32700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',242000,0,33700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',245000,0,34700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',248000,0,35700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',251000,0,36700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',254000,0,37700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',257000,0,38600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',260000,0,39600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',263000,0,40600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',266000,0,41600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',269000,0,42600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',272000,0,43600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',275000,0,44600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',278000,0,45600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',281000,0,46600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',284000,0,47600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',287000,0,48500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',290000,0,49500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',293000,0,50500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',296000,0,51200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',299000,0,51800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',302000,0,52400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',305000,0,53100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',308000,0,53700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',311000,0,54300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',314000,0,54900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',317000,0,55600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',320000,0,56500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',323000,0,57300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',326000,0,58100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',329000,0,59000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',332000,0,59800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',335000,0,60700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',338000,0,61600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',341000,0,62500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',344000,0,63400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',347000,0,64400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',350000,0,65300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',353000,0,66200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',356000,0,67100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',359000,0,68000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',362000,0,69000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',365000,0,69900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',368000,0,70800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',371000,0,71600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',374000,0,72400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',377000,0,73200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',380000,0,74100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',383000,0,74900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',386000,0,75700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',389000,0,76600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',392000,0,78100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',395000,0,79700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',398000,0,81200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',401000,0,82800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',404000,0,84300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',407000,0,85900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',410000,0,87400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',413000,0,88900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',416000,0,90500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',419000,0,92000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',422000,0,93600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',425000,0,95100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',428000,0,96600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',431000,0,98200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',434000,0,99700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',437000,0,101300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',440000,0,102800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',443000,0,104400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',446000,0,105900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',449000,0,107400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',452000,0,109000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',455000,0,110500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',458000,0,112100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',461000,0,113600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',464000,0,115100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',467000,0,116700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',470000,0,118200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',473000,0,119800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',476000,0,121300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',479000,0,122800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',482000,0,124400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',485000,0,125900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',488000,0,127500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',491000,0,129000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',494000,0,130600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',497000,0,132100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',500000,0,133600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',503000,0,135200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',506000,0,136700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',509000,0,138300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',512000,0,139800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',515000,0,141300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',518000,0,142900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',521000,0,144400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',524000,0,146000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',527000,0,147500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',530000,0,148900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',533000,0,150300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',536000,0,151700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',539000,0,153200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',542000,0,154600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',545000,0,156000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',548000,0,157400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',551000,0,158800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',554000,0,160300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',557000,0,161700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',560000,0,163000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',563000,0,164400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',566000,0,165800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',569000,0,167100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',572000,0,168500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',575000,0,169900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',578000,0,171200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',581000,0,172600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',584000,0,174000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',587000,0,175300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',590000,0,176700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',593000,0,178100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',596000,0,179400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',599000,0,180800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',602000,0,182200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',605000,0,183500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',608000,0,184900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',611000,0,186300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',614000,0,187700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',617000,0,189000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',620000,0,190400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',623000,0,191800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',626000,0,193100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',629000,0,194500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',632000,0,195900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',635000,0,197200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',638000,0,198600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',641000,0,200000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',644000,0,201300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',647000,0,202700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',650000,0,203800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',653000,0,204700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',656000,0,205700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',659000,0,206600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',662000,0,207500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',665000,0,208500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',668000,0,209400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',671000,0,210400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',674000,0,211300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',677000,0,212200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',680000,0,213200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',683000,0,214100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',686000,0,215100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',689000,0,216000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',692000,0,216900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',695000,0,217900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',698000,0,218800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',701000,0,219800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',704000,0,220700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',707000,0,222100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',710000,0,223500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',713000,0,224900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',716000,0,226400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',719000,0,227800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',722000,0,229200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',725000,0,230700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',728000,0,232100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',731000,0,233600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',734000,0,235000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',737000,0,236400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',740000,0,237900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',743000,0,239300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',746000,0,240800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',749000,0,242200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',752000,0,243600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',755000,0,245100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',758000,0,246500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',761000,0,248000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',764000,0,249400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',767000,0,250800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',770000,0,252300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',773000,0,253700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',776000,0,255200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',779000,0,256600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',782000,0,258000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',785000,0,259500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',788000,0,260900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',791000,0,262300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',794000,0,263800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',797000,0,265200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',800000,0,266700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',803000,0,268100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',806000,0,269500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',809000,0,271000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',812000,0,272400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',815000,0,273900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',818000,0,275300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',821000,0,276700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',824000,0,278200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',827000,0,279600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',830000,0,281100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',833000,0,282500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',836000,0,283900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',839000,0,285400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',842000,0,286800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',845000,0,288300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',848000,0,289700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',851000,0,291100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',854000,0,292600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',857000,0,294000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',860000,0,295500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',863000,0,296900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',866000,0,298300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',869000,0,299800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',872000,0,301200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',875000,0,302600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',878000,0,304100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',881000,0,305500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',884000,0,307000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',887000,0,308400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',890000,0,309800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',893000,0,311300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',896000,0,312700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',899000,0,314200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',902000,0,315600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',905000,0,317000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',908000,0,318500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',911000,0,319900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',914000,0,321400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',917000,0,322800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',920000,0,324200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',923000,0,325700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',926000,0,327100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',929000,0,328600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',932000,0,330000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',935000,0,331400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',938000,0,332900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',941000,0,334300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',944000,0,335800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',947000,0,337200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',950000,0,338600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',953000,0,340100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',956000,0,341500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',959000,0,342900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',962000,0,344400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',965000,0,345800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',968000,0,347300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',971000,0,348700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',974000,0,350100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',977000,0,351600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',980000,0,353000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',983000,0,354500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',986000,0,355900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',989000,0,357300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',992000,0,358800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',995000,0,360200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',998000,0,361700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',1001000,0,363100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',1004000,0,364500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',1007000,0,366000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2010-01-01',1010000,38,367400,1010000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',0,3.063,0,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',88000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',89000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',90000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',91000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',92000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',93000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',94000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',95000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',96000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',97000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',98000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',99000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',101000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',103000,0,3700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',105000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',107000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',109000,0,3900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',111000,0,4000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',113000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',115000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',117000,0,4200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',119000,0,4300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',121000,0,4500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',123000,0,4800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',125000,0,5100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',127000,0,5400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',129000,0,5700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',131000,0,6000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',133000,0,6300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',135000,0,6600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',137000,0,6800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',139000,0,7100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',141000,0,7500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',143000,0,7800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',145000,0,8100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',147000,0,8400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',149000,0,8700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',151000,0,9000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',153000,0,9300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',155000,0,9600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',157000,0,9900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',159000,0,10200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',161000,0,10500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',163000,0,10800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',165000,0,11100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',167000,0,11400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',169000,0,11700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',171000,0,12000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',173000,0,12400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',175000,0,12700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',177000,0,13200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',179000,0,13900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',181000,0,14600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',183000,0,15300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',185000,0,16000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',187000,0,16700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',189000,0,17500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',191000,0,18100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',193000,0,18800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',195000,0,19500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',197000,0,20200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',199000,0,20900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',201000,0,21500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',203000,0,22200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',205000,0,22700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',207000,0,23300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',209000,0,23900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',211000,0,24400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',213000,0,25000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',215000,0,25500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',217000,0,26100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',219000,0,26800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',221000,0,27400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',224000,0,28400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',227000,0,29300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',230000,0,30300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',233000,0,31300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',236000,0,32400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',239000,0,33400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',242000,0,34400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',245000,0,35400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',248000,0,36400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',251000,0,37500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',254000,0,38500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',257000,0,39400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',260000,0,40400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',263000,0,41500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',266000,0,42500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',269000,0,43500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',272000,0,44500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',275000,0,45500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',278000,0,46600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',281000,0,47600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',284000,0,48600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',287000,0,49500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',290000,0,50500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',293000,0,51600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',296000,0,52300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',299000,0,52900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',302000,0,53500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',305000,0,54200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',308000,0,54800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',311000,0,55400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',314000,0,56100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',317000,0,56800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',320000,0,57700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',323000,0,58500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',326000,0,59300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',329000,0,60200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',332000,0,61100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',335000,0,62000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',338000,0,62900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',341000,0,63800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',344000,0,64700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',347000,0,65800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',350000,0,66700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',353000,0,67600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',356000,0,68500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',359000,0,69400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',362000,0,70400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',365000,0,71400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',368000,0,72300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',371000,0,73100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',374000,0,73900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',377000,0,74700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',380000,0,75700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',383000,0,76500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',386000,0,77300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',389000,0,78200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',392000,0,79700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',395000,0,81400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',398000,0,82900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',401000,0,84500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',404000,0,86100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',407000,0,87700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',410000,0,89200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',413000,0,90800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',416000,0,92400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',419000,0,93900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',422000,0,95600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',425000,0,97100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',428000,0,98600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',431000,0,100300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',434000,0,101800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',437000,0,103400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',440000,0,105000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',443000,0,106600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',446000,0,108100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',449000,0,109700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',452000,0,111300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',455000,0,112800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',458000,0,114500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',461000,0,116000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',464000,0,117500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',467000,0,119200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',470000,0,120700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',473000,0,122300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',476000,0,123800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',479000,0,125400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',482000,0,127000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',485000,0,128500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',488000,0,130200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',491000,0,131700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',494000,0,133300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',497000,0,134900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',500000,0,136400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',503000,0,138100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',506000,0,139900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',509000,0,141500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',512000,0,143200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',515000,0,145000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',518000,0,146600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',521000,0,148400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',524000,0,150100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',527000,0,151700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',530000,0,153300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',533000,0,154900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',536000,0,156400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',539000,0,158100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',542000,0,159600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',545000,0,161200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',548000,0,162700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',551000,0,164300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',554000,0,165900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',557000,0,167400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',560000,0,169000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',563000,0,170500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',566000,0,172000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',569000,0,173600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',572000,0,175100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',575000,0,176600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',578000,0,178200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',581000,0,179600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',584000,0,181100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',587000,0,182700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',590000,0,184200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',593000,0,185700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',596000,0,187300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',599000,0,188800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',602000,0,190300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',605000,0,191800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',608000,0,193400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',611000,0,194900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',614000,0,196400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',617000,0,197900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',620000,0,199400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',623000,0,200900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',626000,0,202500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',629000,0,204000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',632000,0,205500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',635000,0,207100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',638000,0,208600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',641000,0,210100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',644000,0,211700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',647000,0,213200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',650000,0,214400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',653000,0,215400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',656000,0,216600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',659000,0,217700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',662000,0,218700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',665000,0,219800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',668000,0,220800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',671000,0,222000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',674000,0,223100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',677000,0,224100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',680000,0,225200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',683000,0,226400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',686000,0,227400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',689000,0,228500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',692000,0,229600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',695000,0,230700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',698000,0,232400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',701000,0,234000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',704000,0,235600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',707000,0,237300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',710000,0,238900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',713000,0,240500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',716000,0,242200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',719000,0,243800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',722000,0,245300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',725000,0,247000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',728000,0,248600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',731000,0,250200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',734000,0,251900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',737000,0,253500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',740000,0,255100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',743000,0,256800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',746000,0,258400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',749000,0,259900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',752000,0,261600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',755000,0,263200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',758000,0,264800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',761000,0,266500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',764000,0,268100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',767000,0,269700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',770000,0,271400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',773000,0,273000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',776000,0,274600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',779000,0,276200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',782000,0,277800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',785000,0,279400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',788000,0,281100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',791000,0,282700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',794000,0,284300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',797000,0,286000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',800000,0,287600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',803000,0,289200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',806000,0,290800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',809000,0,292400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',812000,0,294000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',815000,0,295700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',818000,0,297300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',821000,0,298900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',824000,0,300600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',827000,0,302200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',830000,0,303800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',833000,0,305400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',836000,0,307000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',839000,0,308500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',842000,0,310100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',845000,0,311600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',848000,0,313100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',851000,0,314700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',854000,0,316300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',857000,0,317800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',860000,0,319400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',863000,0,320900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',866000,0,322400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',869000,0,324000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',872000,0,325600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',875000,0,327100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',878000,0,328700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',881000,0,330200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',884000,0,331700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',887000,0,333300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',890000,0,334800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',893000,0,336400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',896000,0,338000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',899000,0,339500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',902000,0,341000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',905000,0,342500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',908000,0,344100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',911000,0,345600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',914000,0,347200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',917000,0,348800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',920000,0,350300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',923000,0,351800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',926000,0,353400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',929000,0,354900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',932000,0,356500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',935000,0,358100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',938000,0,359600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',941000,0,361100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',944000,0,362700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',947000,0,364200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',950000,0,365700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',953000,0,367400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',956000,0,368900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',959000,0,370400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',962000,0,372000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',965000,0,373500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',968000,0,375000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',971000,0,376500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',974000,0,378200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',977000,0,379700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',980000,0,381200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',983000,0,382800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',986000,0,384300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',989000,0,385800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',992000,0,387500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',995000,0,389000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',998000,0,390500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',1001000,0,392100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',1004000,0,393600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',1007000,0,395100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2013-01-01',1010000,40.84,396700,1010000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',0,3.063,0,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',88000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',89000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',90000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',91000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',92000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',93000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',94000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',95000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',96000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',97000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',98000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',99000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',101000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',103000,0,3700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',105000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',107000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',109000,0,3900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',111000,0,4000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',113000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',115000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',117000,0,4200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',119000,0,4300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',121000,0,4500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',123000,0,4800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',125000,0,5100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',127000,0,5400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',129000,0,5700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',131000,0,6000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',133000,0,6300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',135000,0,6600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',137000,0,6800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',139000,0,7100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',141000,0,7500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',143000,0,7800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',145000,0,8100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',147000,0,8400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',149000,0,8700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',151000,0,9000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',153000,0,9300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',155000,0,9600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',157000,0,9900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',159000,0,10200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',161000,0,10500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',163000,0,10800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',165000,0,11100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',167000,0,11400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',169000,0,11700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',171000,0,12000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',173000,0,12400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',175000,0,12700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',177000,0,13200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',179000,0,13900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',181000,0,14600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',183000,0,15300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',185000,0,16000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',187000,0,16700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',189000,0,17500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',191000,0,18100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',193000,0,18800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',195000,0,19500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',197000,0,20200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',199000,0,20900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',201000,0,21500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',203000,0,22200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',205000,0,22700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',207000,0,23300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',209000,0,23900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',211000,0,24400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',213000,0,25000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',215000,0,25500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',217000,0,26100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',219000,0,26800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',221000,0,27400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',224000,0,28400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',227000,0,29300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',230000,0,30300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',233000,0,31300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',236000,0,32400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',239000,0,33400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',242000,0,34400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',245000,0,35400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',248000,0,36400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',251000,0,37500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',254000,0,38500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',257000,0,39400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',260000,0,40400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',263000,0,41500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',266000,0,42500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',269000,0,43500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',272000,0,44500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',275000,0,45500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',278000,0,46600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',281000,0,47600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',284000,0,48600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',287000,0,49500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',290000,0,50500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',293000,0,51600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',296000,0,52300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',299000,0,52900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',302000,0,53500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',305000,0,54200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',308000,0,54800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',311000,0,55400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',314000,0,56100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',317000,0,56800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',320000,0,57700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',323000,0,58500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',326000,0,59300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',329000,0,60200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',332000,0,61100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',335000,0,62000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',338000,0,62900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',341000,0,63800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',344000,0,64700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',347000,0,65800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',350000,0,66700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',353000,0,67600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',356000,0,68500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',359000,0,69400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',362000,0,70400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',365000,0,71400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',368000,0,72300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',371000,0,73100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',374000,0,73900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',377000,0,74700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',380000,0,75700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',383000,0,76500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',386000,0,77300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',389000,0,78200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',392000,0,79700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',395000,0,81400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',398000,0,82900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',401000,0,84500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',404000,0,86100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',407000,0,87700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',410000,0,89200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',413000,0,90800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',416000,0,92400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',419000,0,93900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',422000,0,95600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',425000,0,97100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',428000,0,98600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',431000,0,100300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',434000,0,101800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',437000,0,103400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',440000,0,105000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',443000,0,106600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',446000,0,108100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',449000,0,109700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',452000,0,111300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',455000,0,112800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',458000,0,114500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',461000,0,116000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',464000,0,117500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',467000,0,119200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',470000,0,120700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',473000,0,122300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',476000,0,123800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',479000,0,125400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',482000,0,127000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',485000,0,128500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',488000,0,130200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',491000,0,131700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',494000,0,133300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',497000,0,134900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',500000,0,136400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',503000,0,138100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',506000,0,139900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',509000,0,141500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',512000,0,143200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',515000,0,145000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',518000,0,146600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',521000,0,148400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',524000,0,150100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',527000,0,151700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',530000,0,153300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',533000,0,154900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',536000,0,156400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',539000,0,158100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',542000,0,159600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',545000,0,161200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',548000,0,162700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',551000,0,164300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',554000,0,165900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',557000,0,167400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',560000,0,169000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',563000,0,170500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',566000,0,172000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',569000,0,173600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',572000,0,175100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',575000,0,176600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',578000,0,178200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',581000,0,179600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',584000,0,181100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',587000,0,182700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',590000,0,184200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',593000,0,185700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',596000,0,187300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',599000,0,188800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',602000,0,190300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',605000,0,191800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',608000,0,193400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',611000,0,194900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',614000,0,196400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',617000,0,197900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',620000,0,199400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',623000,0,200900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',626000,0,202500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',629000,0,204000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',632000,0,205500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',635000,0,207100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',638000,0,208600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',641000,0,210100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',644000,0,211700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',647000,0,213200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',650000,0,214400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',653000,0,215400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',656000,0,216600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',659000,0,217700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',662000,0,218700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',665000,0,219800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',668000,0,220800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',671000,0,222000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',674000,0,223100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',677000,0,224100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',680000,0,225200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',683000,0,226400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',686000,0,227400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',689000,0,228500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',692000,0,229600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',695000,0,230700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',698000,0,232400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',701000,0,234000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',704000,0,235600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',707000,0,237300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',710000,0,238900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',713000,0,240500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',716000,0,242200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',719000,0,243800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',722000,0,245300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',725000,0,247000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',728000,0,248600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',731000,0,250200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',734000,0,251900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',737000,0,253500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',740000,0,255100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',743000,0,256800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',746000,0,258400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',749000,0,259900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',752000,0,261600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',755000,0,263200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',758000,0,264800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',761000,0,266500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',764000,0,268100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',767000,0,269700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',770000,0,271400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',773000,0,273000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',776000,0,274600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',779000,0,276200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',782000,0,277800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',785000,0,279400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',788000,0,281100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',791000,0,282700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',794000,0,284300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',797000,0,286000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',800000,0,287600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',803000,0,289200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',806000,0,290800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',809000,0,292400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',812000,0,294000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',815000,0,295700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',818000,0,297300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',821000,0,298900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',824000,0,300600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',827000,0,302200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',830000,0,303800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',833000,0,305400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',836000,0,307000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',839000,0,308500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',842000,0,310100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',845000,0,311600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',848000,0,313100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',851000,0,314700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',854000,0,316300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',857000,0,317800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',860000,0,319400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',863000,0,320900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',866000,0,322400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',869000,0,324000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',872000,0,325600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',875000,0,327100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',878000,0,328700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',881000,0,330200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',884000,0,331700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',887000,0,333300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',890000,0,334800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',893000,0,336400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',896000,0,338000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',899000,0,339500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',902000,0,341000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',905000,0,342500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',908000,0,344100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',911000,0,345600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',914000,0,347200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',917000,0,348800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',920000,0,350300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',923000,0,351800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',926000,0,353400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',929000,0,354900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',932000,0,356500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',935000,0,358100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',938000,0,359600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',941000,0,361100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',944000,0,362700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',947000,0,364200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',950000,0,365700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',953000,0,367400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',956000,0,368900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',959000,0,370400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',962000,0,372000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',965000,0,373500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',968000,0,375000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',971000,0,376500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',974000,0,378200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',977000,0,379700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',980000,0,381200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',983000,0,382800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',986000,0,384300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',989000,0,385800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',992000,0,387500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',995000,0,389000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',998000,0,390500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',1001000,0,392100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',1004000,0,393600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',1007000,0,395100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',1010000,40.84,396700,1010000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2015-01-01',1250000,45.945,494800,1250000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',0,3.063,0,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',88000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',89000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',90000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',91000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',92000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',93000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',94000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',95000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',96000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',97000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',98000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',99000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',101000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',103000,0,3700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',105000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',107000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',109000,0,3900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',111000,0,4000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',113000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',115000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',117000,0,4200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',119000,0,4300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',121000,0,4500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',123000,0,4800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',125000,0,5100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',127000,0,5400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',129000,0,5700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',131000,0,6000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',133000,0,6300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',135000,0,6600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',137000,0,6800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',139000,0,7100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',141000,0,7500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',143000,0,7800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',145000,0,8100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',147000,0,8400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',149000,0,8700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',151000,0,9000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',153000,0,9300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',155000,0,9600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',157000,0,9900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',159000,0,10200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',161000,0,10500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',163000,0,10800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',165000,0,11100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',167000,0,11400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',169000,0,11700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',171000,0,12000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',173000,0,12400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',175000,0,12700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',177000,0,13200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',179000,0,13900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',181000,0,14600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',183000,0,15300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',185000,0,16000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',187000,0,16700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',189000,0,17500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',191000,0,18100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',193000,0,18800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',195000,0,19500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',197000,0,20200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',199000,0,20900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',201000,0,21500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',203000,0,22200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',205000,0,22700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',207000,0,23300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',209000,0,23900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',211000,0,24400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',213000,0,25000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',215000,0,25500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',217000,0,26100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',219000,0,26800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',221000,0,27400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',224000,0,28400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',227000,0,29300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',230000,0,30300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',233000,0,31300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',236000,0,32400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',239000,0,33400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',242000,0,34400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',245000,0,35400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',248000,0,36400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',251000,0,37500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',254000,0,38500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',257000,0,39400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',260000,0,40400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',263000,0,41500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',266000,0,42500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',269000,0,43500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',272000,0,44500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',275000,0,45500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',278000,0,46600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',281000,0,47600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',284000,0,48600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',287000,0,49500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',290000,0,50500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',293000,0,51600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',296000,0,52300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',299000,0,52900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',302000,0,53500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',305000,0,54200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',308000,0,54800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',311000,0,55400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',314000,0,56100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',317000,0,56800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',320000,0,57700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',323000,0,58500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',326000,0,59300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',329000,0,60200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',332000,0,61100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',335000,0,62000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',338000,0,62900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',341000,0,63800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',344000,0,64700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',347000,0,65800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',350000,0,66700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',353000,0,67600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',356000,0,68500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',359000,0,69400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',362000,0,70400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',365000,0,71400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',368000,0,72300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',371000,0,73100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',374000,0,73900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',377000,0,74700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',380000,0,75700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',383000,0,76500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',386000,0,77300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',389000,0,78200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',392000,0,79700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',395000,0,81400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',398000,0,82900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',401000,0,84500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',404000,0,86300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',407000,0,87900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',410000,0,89600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',413000,0,91400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',416000,0,93000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',419000,0,94700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',422000,0,96500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',425000,0,98100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',428000,0,99900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',431000,0,101600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',434000,0,103200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',437000,0,105000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',440000,0,106700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',443000,0,108300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',446000,0,110100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',449000,0,111800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',452000,0,113400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',455000,0,115200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',458000,0,116800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',461000,0,118500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',464000,0,120300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',467000,0,121900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',470000,0,123600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',473000,0,125400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',476000,0,127000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',479000,0,128700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',482000,0,130500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',485000,0,132100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',488000,0,133900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',491000,0,135600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',494000,0,137200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',497000,0,139000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',500000,0,140700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',503000,0,142300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',506000,0,144100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',509000,0,145800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',512000,0,147400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',515000,0,149200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',518000,0,150800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',521000,0,152500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',524000,0,154300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',527000,0,155900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',530000,0,157500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',533000,0,159100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',536000,0,160700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',539000,0,162200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',542000,0,163800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',545000,0,165400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',548000,0,166900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',551000,0,168600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',554000,0,170100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',557000,0,171600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',560000,0,173200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',563000,0,174700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',566000,0,176200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',569000,0,177800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',572000,0,179300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',575000,0,180800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',578000,0,182400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',581000,0,183900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',584000,0,185400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',587000,0,186900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',590000,0,188400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',593000,0,189900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',596000,0,191400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',599000,0,193000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',602000,0,194500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',605000,0,196000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',608000,0,197600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',611000,0,199100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',614000,0,200600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',617000,0,202200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',620000,0,203700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',623000,0,205200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',626000,0,206700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',629000,0,208200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',632000,0,209700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',635000,0,211200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',638000,0,212800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',641000,0,214300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',644000,0,215800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',647000,0,217400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',650000,0,218600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',653000,0,219700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',656000,0,220700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',659000,0,221900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',662000,0,222900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',665000,0,224000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',668000,0,225000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',671000,0,226000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',674000,0,227100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',677000,0,228100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',680000,0,229100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',683000,0,230100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',686000,0,231200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',689000,0,232200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',692000,0,233600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',695000,0,235100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',698000,0,236800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',701000,0,238300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',704000,0,239800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',707000,0,241400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',710000,0,242900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',713000,0,244400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',716000,0,246000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',719000,0,247600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',722000,0,249100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',725000,0,250700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',728000,0,252200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',731000,0,253700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',734000,0,255300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',737000,0,256900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',740000,0,258400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',743000,0,259900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',746000,0,261500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',749000,0,263000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',752000,0,264500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',755000,0,266100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',758000,0,267700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',761000,0,269200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',764000,0,270800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',767000,0,272300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',770000,0,273800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',773000,0,275400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',776000,0,276900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',779000,0,278500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',782000,0,280100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',785000,0,281600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',788000,0,283100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',791000,0,284700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',794000,0,286200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',797000,0,287800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',800000,0,289400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',803000,0,290900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',806000,0,292400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',809000,0,293900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',812000,0,295500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',815000,0,297000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',818000,0,298600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',821000,0,300200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',824000,0,301700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',827000,0,303200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',830000,0,304800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',833000,0,306300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',836000,0,307800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',839000,0,309500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',842000,0,311000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',845000,0,312500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',848000,0,314100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',851000,0,315600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',854000,0,317100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',857000,0,318800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',860000,0,320300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',863000,0,321800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',866000,0,323400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',869000,0,324900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',872000,0,326400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',875000,0,327900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',878000,0,329600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',881000,0,331100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',884000,0,332600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',887000,0,334200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',890000,0,335700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',893000,0,337200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',896000,0,338800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',899000,0,340400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',902000,0,341900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',905000,0,343500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',908000,0,345000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',911000,0,346500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',914000,0,348100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',917000,0,349700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',920000,0,351200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',923000,0,352800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',926000,0,354300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',929000,0,355800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',932000,0,357400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',935000,0,358900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',938000,0,360500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',941000,0,362000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',944000,0,363600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',947000,0,365100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',950000,0,366600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',953000,0,368200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',956000,0,369700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',959000,0,371300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',962000,0,372900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',965000,0,374400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',968000,0,375900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',971000,0,377500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',974000,0,379000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',977000,0,380600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',980000,0,382200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',983000,0,383700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',986000,0,385200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',989000,0,386800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',992000,0,388300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',995000,0,389800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',998000,0,391500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',1001000,0,393000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',1004000,0,394500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',1007000,0,396000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',1010000,40.84,397600,1010000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2016-01-01',1720000,45.945,687600,1720000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',0,3.063,0,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',88000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',89000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',90000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',91000,0,3200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',92000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',93000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',94000,0,3300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',95000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',96000,0,3400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',97000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',98000,0,3500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',99000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',101000,0,3600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',103000,0,3700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',105000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',107000,0,3800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',109000,0,3900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',111000,0,4000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',113000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',115000,0,4100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',117000,0,4200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',119000,0,4300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',121000,0,4500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',123000,0,4800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',125000,0,5100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',127000,0,5400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',129000,0,5700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',131000,0,6000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',133000,0,6300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',135000,0,6600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',137000,0,6800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',139000,0,7100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',141000,0,7500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',143000,0,7800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',145000,0,8100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',147000,0,8400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',149000,0,8700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',151000,0,9000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',153000,0,9300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',155000,0,9600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',157000,0,9900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',159000,0,10200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',161000,0,10500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',163000,0,10800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',165000,0,11100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',167000,0,11400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',169000,0,11700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',171000,0,12000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',173000,0,12400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',175000,0,12700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',177000,0,13200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',179000,0,13900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',181000,0,14600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',183000,0,15300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',185000,0,16000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',187000,0,16700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',189000,0,17500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',191000,0,18100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',193000,0,18800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',195000,0,19500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',197000,0,20200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',199000,0,20900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',201000,0,21500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',203000,0,22200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',205000,0,22700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',207000,0,23300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',209000,0,23900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',211000,0,24400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',213000,0,25000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',215000,0,25500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',217000,0,26100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',219000,0,26800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',221000,0,27400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',224000,0,28400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',227000,0,29300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',230000,0,30300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',233000,0,31300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',236000,0,32400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',239000,0,33400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',242000,0,34400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',245000,0,35400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',248000,0,36400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',251000,0,37500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',254000,0,38500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',257000,0,39400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',260000,0,40400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',263000,0,41500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',266000,0,42500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',269000,0,43500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',272000,0,44500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',275000,0,45500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',278000,0,46600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',281000,0,47600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',284000,0,48600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',287000,0,49500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',290000,0,50500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',293000,0,51600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',296000,0,52300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',299000,0,52900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',302000,0,53500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',305000,0,54200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',308000,0,54800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',311000,0,55400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',314000,0,56100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',317000,0,56800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',320000,0,57700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',323000,0,58500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',326000,0,59300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',329000,0,60200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',332000,0,61100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',335000,0,62000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',338000,0,63000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',341000,0,64000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',344000,0,65000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',347000,0,66200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',350000,0,67200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',353000,0,68200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',356000,0,69200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',359000,0,70200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',362000,0,71300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',365000,0,72300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',368000,0,73200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',371000,0,74200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',374000,0,75100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',377000,0,76100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',380000,0,77000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',383000,0,77900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',386000,0,78800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',389000,0,80600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',392000,0,82300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',395000,0,83900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',398000,0,85700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',401000,0,87400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',404000,0,89000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',407000,0,90800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',410000,0,92500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',413000,0,94100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',416000,0,95900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',419000,0,97600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',422000,0,99200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',425000,0,101000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',428000,0,102600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',431000,0,104300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',434000,0,106100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',437000,0,107700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',440000,0,109500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',443000,0,111200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',446000,0,112800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',449000,0,114600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',452000,0,116300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',455000,0,117900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',458000,0,119700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',461000,0,121400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',464000,0,123000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',467000,0,124800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',470000,0,126500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',473000,0,128100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',476000,0,129900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',479000,0,131600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',482000,0,133200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',485000,0,135000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',488000,0,136600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',491000,0,138300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',494000,0,140100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',497000,0,141700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',500000,0,143500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',503000,0,145200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',506000,0,146800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',509000,0,148600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',512000,0,150300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',515000,0,151900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',518000,0,153700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',521000,0,155400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',524000,0,157000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',527000,0,158800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',530000,0,160300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',533000,0,161900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',536000,0,163500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',539000,0,165000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',542000,0,166600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',545000,0,168200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',548000,0,169800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',551000,0,171300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',554000,0,173000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',557000,0,174500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',560000,0,175900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',563000,0,177300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',566000,0,178900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',569000,0,180300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',572000,0,181800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',575000,0,183300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',578000,0,184700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',581000,0,186200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',584000,0,187700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',587000,0,189200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',590000,0,190600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',593000,0,192100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',596000,0,193600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',599000,0,195000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',602000,0,196500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',605000,0,198000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',608000,0,199400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',611000,0,200900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',614000,0,202400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',617000,0,203900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',620000,0,205300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',623000,0,206800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',626000,0,208300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',629000,0,209700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',632000,0,211200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',635000,0,212700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',638000,0,214100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',641000,0,215600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',644000,0,217000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',647000,0,218000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',650000,0,219000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',653000,0,220000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',656000,0,221000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',659000,0,222100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',662000,0,223100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',665000,0,224100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',668000,0,225000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',671000,0,226000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',674000,0,227100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',677000,0,228100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',680000,0,229100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',683000,0,230100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',686000,0,231200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',689000,0,232700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',692000,0,234200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',695000,0,235700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',698000,0,237300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',701000,0,238900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',704000,0,240400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',707000,0,242000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',710000,0,243500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',713000,0,245000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',716000,0,246600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',719000,0,248100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',722000,0,249700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',725000,0,251300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',728000,0,252800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',731000,0,254300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',734000,0,255900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',737000,0,257400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',740000,0,259000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',743000,0,260600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',746000,0,262100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',749000,0,263600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',752000,0,265200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',755000,0,266700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',758000,0,268200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',761000,0,269900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',764000,0,271400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',767000,0,272900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',770000,0,274400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',773000,0,276000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',776000,0,277500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',779000,0,279000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',782000,0,280700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',785000,0,282200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',788000,0,283700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',791000,0,285300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',794000,0,286800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',797000,0,288300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',800000,0,290000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',803000,0,291500,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',806000,0,293000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',809000,0,294600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',812000,0,296100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',815000,0,297600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',818000,0,299200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',821000,0,300800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',824000,0,302300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',827000,0,303800,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',830000,0,305400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',833000,0,306900,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',836000,0,308400,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',839000,0,310000,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',842000,0,311600,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',845000,0,313100,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',848000,0,314700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',851000,0,316200,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',854000,0,317700,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',857000,0,319300,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',860000,40.84,320900,860000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_o_tax_id_seq'),'2017-01-01',1720000,45.945,672200,1720000,0,now(),'system',now(),'system')
;


INSERT INTO prm_payroll_tax(
	prm_payroll_tax_id, 
	execute_date, 
	above, 
	tax_rate, 
	tax_price, 
	delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_payroll_tax_id_seq'),'2010-01-01',0,5,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2010-01-01',162501,10,8125,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2010-01-01',275001,20,35625,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2010-01-01',579167,23,53000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2010-01-01',750001,33,128000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2010-01-01',1500001,40,233000,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2013-01-01',0,5.105,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2013-01-01',162501,10.21,8296,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2013-01-01',275001,20.42,36374,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2013-01-01',579167,23.483,54113,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2013-01-01',750001,33.693,130688,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2013-01-01',1500001,40.84,237893,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2015-01-01',0,5.105,0,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2015-01-01',162501,10.21,8296,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2015-01-01',275001,20.42,36374,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2015-01-01',579167,23.483,54113,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2015-01-01',750001,33.693,130688,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2015-01-01',1500001,40.84,237893,0,now(),'system',now(),'system')
,(nextval('prm_payroll_tax_id_seq'),'2015-01-01',3333334,45.945,408061,0,now(),'system',now(),'system')
;


INSERT INTO prm_system_parameter(
            prm_system_parameter_id, 
            company_code, 
            payroll_or_bonus, 
            item_number, 
            scr_parameter_master, 
            scr_payroll_system, 
            scr_detail_item, 
            scr_payroll_item, 
            scr_total_item, 
            scr_calc_correction, 
            scr_detail_correction, 
            scr_payroll_scale, 
            scr_formula_master, 
            scr_calc_item, 
            scr_unit_price, 
            scr_allowance_payment, 
            scr_allowance_deduction, 
            csv_item_type, 
            delete_flag, insert_date, insert_user, update_date, update_user)
VALUES 
 (nextval('prm_system_parameter_id_seq'),1,1,'001',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'002',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'003',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'004',1,1,11,31,1,1,2,0,0,1,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'005',1,1,11,31,1,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'010',1,1,11,31,1,0,0,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'011',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'012',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'013',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'014',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'015',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'016',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'017',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'018',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'019',1,1,11,31,1,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'020',1,1,11,31,1,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'030',1,1,12,32,2,0,0,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'031',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'032',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'033',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'034',1,1,12,32,2,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'035',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'036',1,1,12,32,2,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'037',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'038',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'039',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'041',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'042',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'043',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'044',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'045',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'046',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'047',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'048',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'049',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'050',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'051',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'052',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'053',1,1,12,32,2,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'054',1,1,12,32,2,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'055',1,1,12,32,2,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'061',1,1,13,33,3,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'062',1,1,13,33,3,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'063',1,1,13,33,3,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'064',1,1,13,33,3,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'065',1,1,13,33,3,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'066',1,1,13,33,3,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'067',1,1,13,33,3,1,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'068',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'069',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'070',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'071',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'072',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'073',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'074',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'075',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'076',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'077',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'078',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'079',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'080',1,1,13,33,3,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'091',1,1,10,30,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'092',1,1,10,30,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'093',1,1,10,30,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'094',1,1,10,30,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'095',1,1,10,30,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'101',2,0,0,0,0,0,2,2,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'102',2,0,0,0,0,0,0,2,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'103',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'104',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'105',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'106',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'107',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'108',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'109',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'110',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'111',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'112',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'113',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'115',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'116',2,0,0,0,0,0,0,0,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'121',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'122',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'123',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'124',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'125',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'126',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'127',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'128',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'129',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'130',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'131',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'132',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'133',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'134',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'135',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'136',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'137',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'138',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'139',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'140',2,0,0,0,0,0,0,1,1,0,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'201',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'202',32,3,20,10,4,3,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'203',32,3,20,10,4,3,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'211',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'212',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'213',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'214',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'215',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'216',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'217',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'218',31,3,20,10,4,3,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'219',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'220',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'221',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'222',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'223',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'224',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'225',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'226',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'227',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'228',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'229',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'230',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'231',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'232',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'233',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'234',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'235',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'236',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'237',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'238',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'239',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'240',31,3,20,10,4,0,3,2,0,0,0,1,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'251',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'252',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'253',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'254',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'255',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'256',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'257',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'258',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'259',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'260',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'261',32,3,20,10,4,3,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'262',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'263',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'264',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'265',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'266',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'267',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'268',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'269',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'270',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'271',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'272',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'273',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'274',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'275',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'276',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'277',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'278',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'279',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'280',32,3,20,10,4,0,3,0,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'301',33,3,20,10,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'302',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'303',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'304',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'305',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'306',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'307',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'308',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'309',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'310',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'311',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'312',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'313',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'314',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'315',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'316',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'317',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'318',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'319',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'320',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'321',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'322',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'323',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'324',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'325',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'326',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'327',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'328',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'329',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'330',33,3,20,10,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'401',33,3,20,10,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'402',33,3,20,10,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'403',33,3,20,10,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'404',33,3,20,10,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'405',33,3,20,10,0,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'406',33,3,20,10,0,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'451',4,4,20,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'452',4,4,50,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'453',4,4,50,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'454',4,4,50,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'455',4,4,50,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'456',4,4,50,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'457',4,4,50,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'458',4,4,50,10,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'501',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'502',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'503',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'504',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'505',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'506',4,4,30,20,0,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'507',4,4,30,20,0,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'511',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'512',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'513',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'514',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'515',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'516',4,4,30,20,0,4,5,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'521',4,4,30,20,4,4,5,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'522',4,4,30,20,0,4,5,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'523',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'524',4,4,30,20,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'601',5,5,30,20,4,5,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'602',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'603',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'604',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'605',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'606',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'607',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'608',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'609',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'610',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'611',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'612',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'613',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'614',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'615',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'616',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'617',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'618',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'619',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'620',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'621',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'622',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'623',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'624',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'625',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'626',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'627',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'628',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'629',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'630',5,5,30,20,4,0,4,2,0,0,0,0,1,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'641',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'642',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'643',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'644',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'645',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'646',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'647',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'648',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'649',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'650',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'651',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'652',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'653',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'654',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'655',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'656',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'657',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'658',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'659',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'660',5,5,30,20,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'671',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'672',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'673',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'674',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'675',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'676',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'677',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'678',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'679',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'680',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'681',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'682',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'683',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'684',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'685',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'686',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'687',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'688',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'689',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'690',5,5,30,20,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'801',5,5,30,20,4,5,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'802',5,5,30,20,0,5,4,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'803',5,5,30,20,4,5,4,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'804',4,4,30,20,0,5,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'805',4,4,50,20,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'806',4,4,50,20,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'807',4,4,50,20,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'808',4,0,50,20,0,0,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'809',4,4,50,20,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'810',4,4,50,20,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'821',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'822',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'823',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'824',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'825',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'901',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'902',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'903',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'904',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'905',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'906',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'907',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'908',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'909',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'910',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'911',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'912',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'913',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'914',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'915',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'916',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'917',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'921',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'922',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'923',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'924',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'925',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'926',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'927',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'928',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'929',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'930',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'931',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'932',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'933',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'934',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'935',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'941',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'942',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'943',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'944',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'945',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'946',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'947',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'948',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'949',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'950',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'951',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'952',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'953',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'954',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'955',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'961',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'962',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'963',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'964',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'965',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'966',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'967',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'968',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'969',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'970',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'971',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'972',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'973',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'974',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'975',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'976',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'977',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'978',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'979',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,1,'980',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'061',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'062',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'063',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'064',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'065',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'091',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'092',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'093',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'094',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'095',1,1,10,0,0,0,2,0,0,1,0,0,0,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'101',2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'102',2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'103',2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'104',2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'251',3,3,20,40,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'252',3,3,20,40,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'253',3,3,20,40,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'254',3,3,20,40,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'255',3,3,20,40,4,3,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'256',3,3,20,40,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'257',3,3,20,40,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'258',3,3,20,40,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'259',3,3,20,40,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'260',3,3,20,40,4,0,3,0,0,1,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'261',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'262',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'263',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'264',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'265',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'266',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'267',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'268',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'269',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'270',3,3,20,40,4,0,3,2,1,0,0,0,0,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'451',4,4,20,40,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'452',4,4,50,40,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'453',4,4,50,40,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'454',4,4,50,40,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'456',4,4,50,40,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'457',4,4,50,40,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'458',4,4,50,40,0,3,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'501',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'502',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'503',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'504',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'505',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'506',4,4,30,50,0,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'507',4,4,30,50,0,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'511',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'512',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'513',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'514',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'515',4,4,30,50,4,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'516',4,4,30,50,0,4,5,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'521',4,4,30,50,4,4,5,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'522',4,4,30,50,0,4,5,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'523',4,4,30,50,0,4,5,0,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'601',5,5,30,50,4,5,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'602',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'603',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'604',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'605',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'606',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'607',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'608',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'609',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'610',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'611',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'612',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'613',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'614',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'615',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'616',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'617',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'618',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'619',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'620',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'621',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'622',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'623',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'624',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'625',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'626',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'627',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'628',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'629',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'630',5,5,30,50,4,0,4,2,0,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'641',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'642',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'643',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'644',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'645',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'646',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'647',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'648',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'649',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'650',5,5,30,50,4,0,4,2,1,0,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'671',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'672',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'673',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'674',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'675',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'676',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'677',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'678',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'679',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'680',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'681',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'682',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'683',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'684',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'685',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'686',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'687',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'688',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'689',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'690',5,5,30,50,4,0,4,0,0,1,0,0,0,3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'803',5,5,30,50,4,5,4,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'804',4,4,30,50,0,5,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'805',4,4,50,50,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'806',4,4,50,50,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'807',4,4,50,50,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'808',4,0,50,50,0,0,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'809',4,4,50,50,0,6,6,0,0,0,0,0,0,4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'821',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'822',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'823',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'824',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'825',4,4,60,0,0,6,6,0,0,0,0,0,0,400,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'901',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'902',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'903',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'904',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'905',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'906',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'907',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'908',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'909',6,0,0,0,0,7,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'910',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'911',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'912',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'913',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'914',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'915',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'916',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'917',0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'921',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'922',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'923',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'924',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'925',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'926',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'927',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'928',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'929',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'930',6,0,0,0,0,9,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'931',6,0,0,0,0,10,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'932',6,0,0,0,0,10,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'933',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'934',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'935',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'941',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'942',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'943',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'944',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'945',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'946',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'947',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'948',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'949',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'950',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'951',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'952',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'953',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'954',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'955',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'961',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'962',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'963',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'964',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'965',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'966',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'967',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'968',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'969',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'970',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'971',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'972',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'973',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'974',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'975',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'976',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'977',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'978',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'979',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_id_seq'),1,2,'980',6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
;


INSERT INTO prm_system_parameter_item(
            prm_system_parameter_item_id, 
            company_code, 
            payroll_or_bonus, 
            item_number, 
            use_type, 
            basic_item_name, 
            view_item_name, 
            formula_type, 
            formula_order, 
            calc_withholding_type, 
            calc_reward_type, 
            calc_employment_type, 
            calc_wage_type, 
            parameter_remark, 
            bonus_basic_type_monthly, 
            bonus_basic_type_daily_monthly, 
            overtime_monthly, 
            overtime_daily_monthly, 
            overtime_daily, 
            overtime_hourly, 
            lateearly_monthly, 
            lateearly_daily_monthly, 
            lateearly_daily, 
            absence_monthly, 
            absence_daily_monthly, 
            delete_flag, insert_date, insert_user, update_date, update_user)
VALUES
 (nextval('prm_system_parameter_item_id_seq'),1,1,'001',1,'出勤日数','出勤日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'002',1,'欠勤日数','欠勤日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'003',1,'有休取得日数','有給休暇日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'004',1,'有休残日数','有休残日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'005',0,'要勤務日数','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'010',0,'日給者日数','日給者日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'011',1,'日数項目1','法定休日出勤日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'012',1,'日数項目2','所定休日出勤日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'013',1,'日数項目3','遅刻日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'014',1,'日数項目4','早退日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'015',1,'日数項目5','休日日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'016',1,'日数項目6','代休日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'017',1,'日数項目7','振替休日日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'018',1,'日数項目8','特別休暇日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'019',1,'日数項目9','その他休暇日数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'020',0,'日数項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'030',0,'時給者時間数','時給者時間数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'031',1,'残業時間','残業時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'032',1,'深夜残業時間','深夜時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'033',1,'法定休日残業時間','法定休出時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'034',0,'法定休日深夜残業時間','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'035',1,'所定休日残業時間','所定休出時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'036',0,'所定休日深夜残業時間','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'037',1,'45時間超割増時間','45時間超残業時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'038',1,'60時間超割増時間','60時間超残業時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'039',1,'遅刻早退等控除時間','減額対象時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'041',1,'時間項目1','勤務時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'042',1,'時間項目2','法定内残業時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'043',1,'時間項目3','法定外残業時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'044',1,'時間項目4','休憩時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'045',1,'時間項目5','深夜休憩時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'046',1,'時間項目6','所定休出休憩時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'047',1,'時間項目7','法定休出休憩時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'048',1,'時間項目8','公用外出時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'049',1,'時間項目9','私用外出時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'050',1,'時間項目10','遅刻時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'051',1,'時間項目11','早退時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'052',1,'時間項目12','有給休暇時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'053',1,'時間項目13','有休残時間',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'054',0,'時間項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'055',0,'時間項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'061',1,'回数項目1','出勤回数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'062',1,'回数項目2','直行回数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'063',1,'回数項目3','直帰回数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'064',1,'回数項目4','残業回数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'065',1,'回数項目5','休日出勤回数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'066',1,'回数項目6','遅刻回数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'067',1,'回数項目7','早退回数',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'068',0,'回数項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'069',0,'回数項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'070',0,'回数項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'071',0,'回数項目11','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'072',0,'回数項目12','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'073',0,'回数項目13','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'074',0,'回数項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'075',0,'回数項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'076',0,'回数項目16','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'077',0,'回数項目17','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'078',0,'回数項目18','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'079',0,'回数項目19','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'080',0,'回数項目20','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'091',0,'倍率項目1','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'092',0,'倍率項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'093',0,'倍率項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'094',0,'倍率項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'095',0,'倍率項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'101',1,'日給単価','日給単価',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'102',1,'時給単価','時給単価',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'103',1,'残業基準単価','残業基準単価',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'104',1,'残業単価','残業単価',1,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'105',1,'深夜残業単価','深夜残業単価',1,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'106',1,'法定休日残業単価','法定休日出勤単価',1,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'107',0,'法定休日深夜残業単価','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'108',0,'所定休日残業単価','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'109',0,'所定休日深夜残業単価','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'110',1,'45時間超割増単価','45時間超割増単価',1,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'111',1,'60時間超割増単価','60時間超割増単価',1,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'112',1,'遅早等控除基準単価','遅早等控除基準単価',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'113',1,'遅早等控除単価','遅早等控除単価',1,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'115',1,'欠勤控除基準単価','欠勤控除基準単価',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'116',1,'欠勤控除単価','欠勤控除単価',1,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'121',0,'単価項目1','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'122',0,'単価項目2','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'123',0,'単価項目3','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'124',0,'単価項目4','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'125',0,'単価項目5','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'126',0,'単価項目6','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'127',0,'単価項目7','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'128',0,'単価項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'129',0,'単価項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'130',0,'単価項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'131',0,'単価項目11','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'132',0,'単価項目12','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'133',0,'単価項目13','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'134',0,'単価項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'135',0,'単価項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'136',0,'単価項目16','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'137',0,'単価項目17','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'138',0,'単価項目18','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'139',0,'単価項目19','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'140',0,'単価項目20','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'201',1,'月給者基本給','月給者基本給',0,0,1,1,1,1,'',0,0,2,2,0,0,2,2,0,2,2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'202',1,'日給者基本給','日給者基本給',1,100,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'203',1,'時給者基本給','時給者基本給',1,100,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'211',1,'固定支給項目1','職能手当',0,0,1,1,1,1,'',0,0,1,1,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'212',1,'固定支給項目2','職務手当',0,0,1,1,1,1,'',0,0,0,0,0,0,1,1,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'213',1,'固定支給項目3','通勤手当',0,0,0,1,1,1,'',0,0,0,0,0,0,0,0,0,1,1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'214',1,'固定支給項目4','住宅手当',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'215',1,'固定支給項目5','家族手当',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'216',1,'固定支給項目6','資格手当',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'217',1,'固定支給項目7','役職手当',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'218',1,'固定支給項目8','役員報酬',0,0,1,1,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'219',0,'固定支給項目9','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'220',0,'固定支給項目10','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'221',0,'固定支給項目11','',0,0,0,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'222',0,'固定支給項目12','',0,0,0,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'223',0,'固定支給項目13','',0,0,0,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'224',0,'固定支給項目14','',0,0,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'225',0,'固定支給項目15','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'226',0,'固定支給項目16','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'227',0,'固定支給項目17','',0,0,0,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'228',0,'固定支給項目18','',0,0,1,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'229',0,'固定支給項目19','',0,0,1,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'230',0,'固定支給項目20','',0,0,1,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'231',0,'固定支給項目21','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'232',0,'固定支給項目22','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'233',0,'固定支給項目23','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'234',0,'固定支給項目24','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'235',0,'固定支給項目25','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'236',0,'固定支給項目26','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'237',0,'固定支給項目27','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'238',0,'固定支給項目28','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'239',0,'固定支給項目29','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'240',0,'固定支給項目30','',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'251',1,'普通残業手当','法定外残業手当',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'252',1,'深夜残業手当','深夜勤務手当',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'253',1,'法定休日残業手当','法定休日出勤手当',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'254',0,'法定休日深夜残業手当','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'255',0,'所定休日残業手当','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'256',0,'所定休日深夜残業手当','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'257',1,'45時間超割増手当','45時間超割増手当',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'258',1,'60時間超割増手当','60時間超割増手当',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'259',1,'遅早等控除額','遅早等控除額',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'260',1,'欠勤控除額','欠勤控除額',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'261',1,'計算支給項目1','法定内残業手当',1,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'262',0,'計算支給項目2','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'263',0,'計算支給項目3','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'264',0,'計算支給項目4','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'265',0,'計算支給項目5','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'266',0,'計算支給項目6','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'267',0,'計算支給項目7','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'268',0,'計算支給項目8','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'269',0,'計算支給項目9','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'270',0,'計算支給項目10','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'271',0,'計算支給項目11','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'272',0,'計算支給項目12','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'273',0,'計算支給項目13','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'274',0,'計算支給項目14','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'275',0,'計算支給項目15','',0,100,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'276',0,'計算支給項目16','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'277',0,'計算支給項目17','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'278',0,'計算支給項目18','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'279',0,'計算支給項目19','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'280',0,'計算支給項目20','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'301',1,'変動支給項目1','申請支払',0,0,0,2,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'302',0,'変動支給項目2','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'303',0,'変動支給項目3','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'304',0,'変動支給項目4','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'305',0,'変動支給項目5','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'306',0,'変動支給項目6','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'307',0,'変動支給項目7','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'308',0,'変動支給項目8','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'309',0,'変動支給項目9','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'310',0,'変動支給項目10','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'311',0,'変動支給項目11','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'312',0,'変動支給項目12','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'313',0,'変動支給項目13','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'314',0,'変動支給項目14','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'315',0,'変動支給項目15','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'316',0,'変動支給項目16','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'317',0,'変動支給項目17','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'318',0,'変動支給項目18','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'319',0,'変動支給項目19','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'320',0,'変動支給項目20','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'321',0,'変動支給項目21','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'322',0,'変動支給項目22','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'323',0,'変動支給項目23','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'324',0,'変動支給項目24','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'325',0,'変動支給項目25','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'326',0,'変動支給項目26','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'327',0,'変動支給項目27','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'328',0,'変動支給項目28','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'329',0,'変動支給項目29','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'330',0,'変動支給項目30','',0,0,1,2,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'401',1,'課税通勤費','課税通勤費',0,0,0,0,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'402',1,'非課税通勤費','非課税通勤費',0,0,0,0,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'403',1,'課税通勤費戻入','課税通勤費戻入',0,0,0,0,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'404',1,'非課税通勤費戻入','非課税通勤費戻入',0,0,0,0,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'405',1,'通勤費現金月割額','通勤費現金月割額',0,0,0,0,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'406',1,'通勤費現物月割額','通勤費現物月割額',0,0,0,0,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'451',1,'総支給額','総支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'452',1,'源泉対象額','源泉対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'453',1,'非課税対象額','非課税対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'454',1,'報酬固定対象額','報酬固定対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'455',1,'報酬変動対象額','報酬変動対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'456',1,'雇用保険対象額','雇用保険対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'457',1,'労災保険対象額','労災保険対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'458',1,'その他支給額','その他支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'501',1,'雇用保険料','雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'502',1,'健康保険料','健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'503',1,'介護保険料','介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'504',1,'厚生年金','厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'505',1,'厚生年金基金','厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'506',1,'健保基本保険料','健保基本保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'507',1,'健保特定保険料','健保特定保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'511',1,'調整雇用保険料','調整雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'512',1,'調整健康保険料','調整健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'513',1,'調整介護保険料','調整介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'514',1,'調整厚生年金','調整厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'515',1,'調整厚生年金基金','調整厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'516',1,'社会保険料計','社会保険料計',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'521',1,'課税調整額','課税調整額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'522',1,'課税対象額','課税対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'523',1,'所得税','所得税',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'524',1,'住民税','住民税',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'601',1,'固定控除項目1','積立金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'602',0,'固定控除項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'603',0,'固定控除項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'604',0,'固定控除項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'605',0,'固定控除項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'606',0,'固定控除項目6','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'607',0,'固定控除項目7','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'608',0,'固定控除項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'609',0,'固定控除項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'610',0,'固定控除項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'611',0,'固定控除項目11','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'612',0,'固定控除項目12','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'613',0,'固定控除項目13','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'614',0,'固定控除項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'615',0,'固定控除項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'616',0,'固定控除項目16','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'617',0,'固定控除項目17','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'618',0,'固定控除項目18','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'619',0,'固定控除項目19','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'620',0,'固定控除項目20','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'621',0,'固定控除項目21','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'622',0,'固定控除項目22','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'623',0,'固定控除項目23','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'624',0,'固定控除項目24','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'625',0,'固定控除項目25','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'626',0,'固定控除項目26','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'627',0,'固定控除項目27','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'628',0,'固定控除項目28','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'629',0,'固定控除項目29','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'630',0,'固定控除項目30','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'641',0,'計算控除項目1','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'642',0,'計算控除項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'643',0,'計算控除項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'644',0,'計算控除項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'645',0,'計算控除項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'646',0,'計算控除項目6','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'647',0,'計算控除項目7','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'648',0,'計算控除項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'649',0,'計算控除項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'650',0,'計算控除項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'651',0,'計算控除項目11','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'652',0,'計算控除項目12','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'653',0,'計算控除項目13','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'654',0,'計算控除項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'655',0,'計算控除項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'656',0,'計算控除項目16','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'657',0,'計算控除項目17','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'658',0,'計算控除項目18','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'659',0,'計算控除項目19','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'660',0,'計算控除項目20','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'671',0,'変動控除項目1','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'672',0,'変動控除項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'673',0,'変動控除項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'674',0,'変動控除項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'675',0,'変動控除項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'676',0,'変動控除項目6','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'677',0,'変動控除項目7','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'678',0,'変動控除項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'679',0,'変動控除項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'680',0,'変動控除項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'681',0,'変動控除項目11','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'682',0,'変動控除項目12','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'683',0,'変動控除項目13','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'684',0,'変動控除項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'685',0,'変動控除項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'686',0,'変動控除項目16','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'687',0,'変動控除項目17','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'688',0,'変動控除項目18','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'689',0,'変動控除項目19','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'690',0,'変動控除項目20','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'801',1,'通勤費控除','通勤費控除',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'802',1,'前月度繰越額','前月度繰越額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'803',1,'年調過不足額','年調過不足額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'804',1,'控除合計','控除合計',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'805',1,'差引支給額','差引支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'806',1,'口座１振込額','口座1振込額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'807',1,'口座２振込額','口座2振込額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'808',0,'口座３振込額','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'809',1,'現金支給額','現金支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'810',1,'翌月繰越額','翌月繰越額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'821',1,'年間総支給額','年間総支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'822',1,'年間源泉対象額','年間源泉対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'823',1,'年間非課税対象額','年間非課税対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'824',1,'年間社会保険料','年間社会保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'825',1,'年間所得税','年間所得税',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'901',1,'雇用保険料(事業主)','雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'902',1,'労災保険料(事業主)','労災保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'903',1,'健康保険料(事業主)','健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'904',1,'介護保険料(事業主)','介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'905',1,'厚生年金(事業主)','厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'906',1,'厚生年金基金(事業主)','厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'907',1,'児童手当拠出金(事業主)','児童手当拠出金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'908',1,'健保基本保険料(事業主)','健保基本保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'909',1,'健保特定保険料(事業主)','健保特定保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'911',1,'調整雇用保険料(事業主)','調整雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'912',1,'調整労災保険料(事業主)','調整労災保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'913',1,'調整健康保険料(事業主)','調整健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'914',1,'調整介護保険料(事業主)','調整介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'915',1,'調整厚生年金(事業主)','調整厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'916',1,'調整厚生年金基金(事業主)','調整厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,1,'917',1,'調整児童手当拠出金(事業主)','調整児童手当拠出金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'061',0,'賞与勤怠項目1','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'062',0,'賞与勤怠項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'063',0,'賞与勤怠項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'064',0,'賞与勤怠項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'065',0,'賞与勤怠項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'091',0,'倍率項目1','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'092',0,'倍率項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'093',0,'倍率項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'094',0,'倍率項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'095',0,'倍率項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'101',0,'完全月給賞与基準額','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'102',0,'日給月給賞与基準額','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'103',0,'日給賞与基準額','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'104',0,'時給賞与基準額','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'251',1,'賞与額1','賞与額1',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'252',1,'賞与額2','賞与額2',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'253',1,'賞与額3','賞与額3',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'254',1,'賞与額4','賞与額4',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'255',1,'賞与額5','賞与額5',0,0,1,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'256',0,'賞与額6','',0,0,0,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'257',0,'賞与額7','',0,0,0,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'258',0,'賞与額8','',0,0,0,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'259',0,'賞与額9','',0,0,0,1,1,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'260',0,'賞与額10','',0,0,0,1,1,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'261',0,'計算支給項目1','',0,100,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'262',0,'計算支給項目2','',0,100,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'263',0,'計算支給項目3','',0,100,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'264',0,'計算支給項目4','',0,0,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'265',0,'計算支給項目5','',0,0,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'266',0,'計算支給項目6','',0,0,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'267',0,'計算支給項目7','',0,0,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'268',0,'計算支給項目8','',0,100,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'269',0,'計算支給項目9','',0,100,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'270',0,'計算支給項目10','',0,100,1,0,0,1,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'451',1,'総支給額','総支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'452',1,'源泉対象額','源泉対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'453',1,'非課税対象額','非課税対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'454',1,'報酬固定対象額','報酬固定対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'456',1,'雇用保険対象額','雇用保険対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'457',1,'労災保険対象額','労災保険対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'458',1,'その他支給額','その他支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'501',1,'雇用保険料','雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'502',1,'健康保険料','健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'503',1,'介護保険料','介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'504',1,'厚生年金','厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'505',1,'厚生年金基金','厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'506',1,'健保基本保険料','健保基本保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'507',1,'健保特定保険料','健保特定保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'511',1,'調整雇用保険料','調整雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'512',1,'調整健康保険料','調整健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'513',1,'調整介護保険料','調整介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'514',1,'調整厚生年金','調整厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'515',1,'調整厚生年金基金','調整厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'516',1,'社会保険料計','社会保険料計',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'521',1,'課税調整額','課税調整額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'522',1,'課税対象額','課税対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'523',1,'所得税','所得税',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'601',1,'賞与控除項目1','積立金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'602',0,'賞与控除項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'603',0,'賞与控除項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'604',0,'賞与控除項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'605',0,'賞与控除項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'606',0,'賞与控除項目6','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'607',0,'賞与控除項目7','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'608',0,'賞与控除項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'609',0,'賞与控除項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'610',0,'賞与控除項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'611',0,'賞与控除項目11','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'612',0,'賞与控除項目12','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'613',0,'賞与控除項目13','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'614',0,'賞与控除項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'615',0,'賞与控除項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'616',0,'賞与控除項目16','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'617',0,'賞与控除項目17','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'618',0,'賞与控除項目18','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'619',0,'賞与控除項目19','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'620',0,'賞与控除項目20','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'621',0,'賞与控除項目21','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'622',0,'賞与控除項目22','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'623',0,'賞与控除項目23','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'624',0,'賞与控除項目24','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'625',0,'賞与控除項目25','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'626',0,'賞与控除項目26','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'627',0,'賞与控除項目27','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'628',0,'賞与控除項目28','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'629',0,'賞与控除項目29','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'630',0,'賞与控除項目30','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'641',0,'計算控除項目1','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'642',0,'計算控除項目2','',0,100,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'643',0,'計算控除項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'644',0,'計算控除項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'645',0,'計算控除項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'646',0,'計算控除項目6','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'647',0,'計算控除項目7','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'648',0,'計算控除項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'649',0,'計算控除項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'650',0,'計算控除項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'671',0,'変動控除項目1','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'672',0,'変動控除項目2','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'673',0,'変動控除項目3','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'674',0,'変動控除項目4','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'675',0,'変動控除項目5','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'676',0,'変動控除項目6','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'677',0,'変動控除項目7','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'678',0,'変動控除項目8','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'679',0,'変動控除項目9','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'680',0,'変動控除項目10','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'681',0,'変動控除項目11','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'682',0,'変動控除項目12','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'683',0,'変動控除項目13','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'684',0,'変動控除項目14','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'685',0,'変動控除項目15','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'686',0,'変動控除項目16','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'687',0,'変動控除項目17','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'688',0,'変動控除項目18','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'689',0,'変動控除項目19','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'690',0,'変動控除項目20','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'803',1,'年調過不足額','年調過不足額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'804',1,'控除合計','控除合計',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'805',1,'差引支給額','差引支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'806',1,'口座１振込額','口座1振込額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'807',1,'口座２振込額','口座2振込額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'808',0,'口座３振込額','',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'809',1,'現金支給額','現金支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'821',1,'年間総支給額','年間総支給額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'822',1,'年間源泉対象額','年間源泉対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'823',1,'年間非課税対象額','年間非課税対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'824',1,'年間社会保険料','年間社会保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'825',1,'年間所得税','年間所得税',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'901',1,'雇用保険料(事業主)','雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'902',1,'労災保険料(事業主)','労災保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'903',1,'健康保険料(事業主)','健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'904',1,'介護保険料(事業主)','介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'905',1,'厚生年金(事業主)','厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'906',1,'厚生年金基金(事業主)','厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'907',1,'児童手当拠出金(事業主)','児童手当拠出金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'908',1,'健保基本保険料(事業主)','健保基本保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'909',1,'健保特定保険料(事業主)','健保特定保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'911',1,'調整雇用保険料(事業主)','調整雇用保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'912',1,'調整労災保険料(事業主)','調整労災保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'913',1,'調整健康保険料(事業主)','調整健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'914',1,'調整介護保険料(事業主)','調整介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'915',1,'調整厚生年金(事業主)','調整厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'916',1,'調整厚生年金基金(事業主)','調整厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'917',1,'調整児童手当拠出金(事業主)','調整児童手当拠出金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'921',1,'既払健保報酬対象額','既払健保報酬対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'922',1,'既払健保標準賞与額','既払健保標準賞与額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'923',1,'既払健康保険料','既払健康保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'924',1,'既払介護保険料','既払介護保険料',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'925',1,'健保標準賞与額','健保標準賞与額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'926',1,'既払厚年報酬対象額','既払厚年報酬対象額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'927',1,'既払厚年標準賞与額','既払厚年標準賞与額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'928',1,'既払厚生年金','既払厚生年金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'929',1,'既払厚生年金基金','既払厚生年金基金',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'930',1,'厚年標準賞与額','厚年標準賞与額',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'931',1,'所得税例外区分','所得税例外区分',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_item_id_seq'),1,2,'932',1,'所得税計算税率','所得税計算税率',0,0,0,0,0,0,'',0,0,0,0,0,0,0,0,0,0,0,0,now(),'system',now(),'system')
;


INSERT INTO prm_system_parameter_type(
            prm_system_parameter_type_id, 
            company_code, 
            payroll_or_bonus, 
            type_code, 
            item_number, 
            type_value, 
            delete_flag, insert_date, insert_user, update_date, update_user)
VALUES
 (nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','001',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','002',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','003',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','004',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','011',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','012',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','013',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','014',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','015',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','016',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','017',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','018',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','019',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','031',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','032',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','033',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','035',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','037',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','038',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','039',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','041',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','042',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','043',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','044',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','045',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','046',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','047',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','048',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','049',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','050',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','051',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','052',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','053',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','061',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','062',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','063',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','064',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','065',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','066',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollAttendance','067',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollDeduction','601',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollDeduction','801',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollDeduction','802',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollDeduction','804',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','101',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','102',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','103',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','104',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','105',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','106',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','110',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','111',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','112',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','113',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','115',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','116',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','452',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','453',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','454',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','455',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','456',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','457',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','458',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','805',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','806',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','807',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','809',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','810',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','821',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','822',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','823',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','824',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollOther','825',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','501',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','502',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','503',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','504',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','505',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','506',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','507',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','511',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','512',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','513',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','514',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','515',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','516',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','521',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','522',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','523',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','524',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','803',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','901',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','902',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','903',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','904',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','905',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','906',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','907',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','908',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','909',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','911',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','912',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','913',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','914',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','915',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','916',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSocialTax','917',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','201',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','202',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','203',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','211',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','212',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','213',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','214',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','215',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','216',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','217',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','218',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','251',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','252',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','253',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','257',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','258',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','259',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','260',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','261',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','301',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','401',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','402',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','403',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','404',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','405',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','406',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'DetailCorrectionPayrollSupply','451',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','001',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','002',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','003',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','004',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','011',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','012',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','013',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','014',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','015',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','016',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','017',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','018',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','019',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','031',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','032',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','033',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','035',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','037',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','038',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','039',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','041',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','042',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','043',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','044',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','045',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','046',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','047',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','048',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','049',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','050',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','051',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','052',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','053',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','061',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','062',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','063',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','064',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','065',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','066',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,1,'TimeImportDatabaseType','067',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusAttendanceSupply','251',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusAttendanceSupply','252',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusAttendanceSupply','253',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusAttendanceSupply','254',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusAttendanceSupply','255',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusAttendanceSupply','451',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusDeduction','804',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','452',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','453',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','454',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','456',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','457',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','458',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','805',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','806',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','807',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','809',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','821',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','822',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','823',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','824',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusOther','825',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','501',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','502',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','503',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','504',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','505',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','506',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','507',1,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','511',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','512',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','513',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','514',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','515',2,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','516',7,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','521',7,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','522',7,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','523',7,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','803',7,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','901',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','902',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','903',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','904',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','905',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','906',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','907',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','908',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','909',3,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','911',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','912',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','913',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','914',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','915',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','916',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','917',4,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','921',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','922',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','923',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','924',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','925',5,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','926',6,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','927',6,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','928',6,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','929',6,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','930',6,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','931',7,0,now(),'system',now(),'system')
,(nextval('prm_system_parameter_type_id_seq'),1,2,'DetailCorrectionBonusSocialTax','932',7,0,now(),'system',now(),'system')
;


