/**
 * 
 */
package jp.mosp.platform.bean.workflow;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.RouteApplicationReferenceDtoInterface;

/**
 *
 */
public interface RouteApplicationReferenceSearchBeanInterface {
	
	/**
	 * 検索条件からルート適用リストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return ルート適用リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<RouteApplicationReferenceDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param employeeCode セットする 社員コード
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param employeeName セットする 社員名
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @param workPlaceCode セットする 勤務地コード
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param employmentCode セットする 雇用契約コード
	 */
	void setEmploymentCode(String employmentCode);
	
	/**
	 * @param sectionCode セットする 所属コード
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param positionCode セットする 職位コード
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param workflowType セットする フロー区分。
	 */
	void setWorkflowType(int workflowType);
	
	/**
	 * @param routeApplicationCode セットする routeApplicationCode
	 */
	void setRouteApplicationCode(String routeApplicationCode);
	
	/**
	 * @param routeApplicationName セットする routeApplicationName
	 */
	void setRouteApplicationName(String routeApplicationName);
	
	/**
	 * @param routeCode セットする routeCode
	 */
	void setRouteCode(String routeCode);
	
	/**
	 * @param routeName セットする routeName
	 */
	void setRouteName(String routeName);
	
	/**
	 * @param approverCode セットする approverCode
	 */
	void setApproverCode(String approverCode);
	
	/**
	 * @param approverName セットする approverName
	 */
	void setApproverName(String approverName);
}
