package jp.mosp.time.bean;

import java.util.Date;

import jp.mosp.framework.base.MospException;

/**
 * 打刻クラスインターフェース。
 */
public interface TimeRecordBeanInterface {
	
	/**
	 * 始業を打刻する。<br>
	 * 対象個人ID及び打刻日時で、打刻する。<br>
	 * <br>
	 * 打刻した日の勤怠情報として、登録する。<br>
	 * 打刻した日の勤怠情報が既に存在する場合は、打刻しない。<br>
	 * <br>
	 * 打刻した勤怠情報は、下書き状態になる。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param recordTime 打刻日時
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordStartWork(String personalId, Date recordTime) throws MospException;
	
	/**
	 * 終業を打刻する。<br>
	 * 対象個人ID及び打刻日時で、打刻する。<br>
	 * <br>
	 * 打刻した日の勤怠情報が存在し終業時間が登録されていない場合は、
	 * 打刻した日の勤怠情報として登録する。<br>
	 * <br>
	 * 打刻した日の勤怠情報が存在しない場合、前日の勤怠情報を確認する。<br>
	 * 前日の勤怠情報が存在し終業時間が登録されていない場合は、
	 * 前日の勤怠情報として登録する。<br>
	 * <br>
	 * 上記の場合以外は、打刻しない。<br>
	 * <br>
	 * 休憩入が入力されており休憩戻が入力されていない場合は、打刻しない。<br>
	 * <br>
	 * 打刻した勤怠情報は、申請状態になる。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param recordTime 打刻日時
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordEndWork(String personalId, Date recordTime) throws MospException;
	
	/**
	 * 休憩入を打刻する。<br>
	 * 対象個人ID及び打刻日時で、打刻する。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param recordTime 打刻日時
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordStartRest(String personalId, Date recordTime) throws MospException;
	
	/**
	 * 休憩戻を打刻する。<br>
	 * 対象個人ID及び打刻日時で、打刻する。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param recordTime 打刻日時
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordEndRest(String personalId, Date recordTime) throws MospException;
	
	/**
	 * 定時終業を打刻する。<br>
	 * <br>
	 * 打刻データについては、終業：recordEndWorkと同様。<br>
	 * <br>
	 * 勤務形態で設定した始業時刻/終業時刻を基準にして、遅刻/早退がいずれも発生しない場合、
	 * 実打刻時刻で残業が発生した場合でも勤務形態で設定した終業時刻で丸めて実打刻、
	 * 丸め打刻に登録される。<br>
	 * 同時に、この勤務日の勤怠承認状況を"承認済"とする。<br>
	 * "承認済"に変更する際は自己承認として処理する。<br>
	 * <br>
	 * 勤務形態で設定した始業時刻/終業時刻を基準にして、遅刻/早退のいずれかが発生する場合、
	 * 画面上にエラーメッセージを表示し、勤怠詳細画面からコメントを入力した上で
	 * 申請しなければならないことを操作者に通知する。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param recordTime 打刻日時
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordRegularEnd(String personalId, Date recordTime) throws MospException;
	
	/**
	 * 残業有終業を打刻する。<br>
	 * <br>
	 * 打刻データについては、終業：recordStartWorkと同様。<br>
	 * <br>
	 * 勤怠詳細画面へ画面遷移する。<br>
	 * 画面遷移時、サーバ時刻を勤怠設定の登録内容通りに丸めた時刻が
	 * 勤怠詳細画面の終業時刻入力欄にデフォルトで入力されている状態とする。
	 * 打刻日時がポータル打刻として登録される。
	 * <br>
	 * @param personalId 対象個人ID
	 * @param recordTime 打刻日時
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordOverEnd(String personalId, Date recordTime) throws MospException;
	
	/**
	 * 出勤を打刻する。<br>
	 * 対象個人ID及び打刻日時で、打刻する。<br>
	 * <br>
	 * 打刻データについては、始業：recordEndWorkと同様。<br>
	 * <br>
	 * 打刻した勤怠情報は、承認済状態になる。<br>
	 * <br>
	 * 勤務形態で設定された始業時刻、終業時刻、休憩時間の通りに勤怠が申請され、
	 * そのまま自己承認で承認済の状態となる。<br>
	 * 始業/終業の実打刻、丸め打刻については勤務形態設定されたものが登録される。
	 * 併せて始業のポータル打刻には打刻時刻が登録される。
	 * 終業のポータル打刻には何も登録されない。
	 * <br>
	 * @param personalId 対象個人ID
	 * @param recordTime 打刻日時
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordRegularWork(String personalId, Date recordTime) throws MospException;
	
	/**
	 * 始業を打刻する。<br>
	 * ログインユーザの個人ID及びシステム日付で、打刻する。<br>
	 * 詳細は、{@link TimeRecordBeanInterface#recordStartWork(String, Date)}参照。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordStartWork() throws MospException;
	
	/**
	 * 終業を打刻する。<br>
	 * ログインユーザの個人ID及びシステム日付で、打刻する。<br>
	 * 詳細は、{@link TimeRecordBeanInterface#recordEndWork(String, Date)}参照。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordEndWork() throws MospException;
	
	/**
	 * 休憩入を打刻する。<br>
	 * ログインユーザの個人ID及びシステム日付で、打刻する。<br>
	 * 詳細は、{@link TimeRecordBeanInterface#recordStartRest(String, Date)}参照。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordStartRest() throws MospException;
	
	/**
	 * 休憩戻を打刻する。<br>
	 * ログインユーザの個人ID及びシステム日付で、打刻する。<br>
	 * 詳細は、{@link TimeRecordBeanInterface#recordEndRest(String, Date)}参照。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordEndRest() throws MospException;
	
	/**
	 * 定時終業を打刻する。<br>
	 * ログインユーザの個人ID及びシステム日付で、打刻する。<br>
	 * 詳細は、{@link TimeRecordBeanInterface#recordRegularEnd(String, Date)}参照。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordRegularEnd() throws MospException;
	
	/**
	 * 残業有終業を打刻する。<br>
	 * ログインユーザの個人ID及びシステム日付で、打刻する。<br>
	 * 詳細は、{@link TimeRecordBeanInterface#recordOverEnd(String, Date)}参照。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordOverEnd() throws MospException;
	
	/**
	 * 出勤を打刻する。<br>
	 * ログインユーザの個人ID及びシステム日付で、打刻する。<br>
	 * 詳細は、{@link TimeRecordBeanInterface#recordRegularWork(String, Date)}参照。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void recordRegularWork() throws MospException;
	
}
