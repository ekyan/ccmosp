/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.mosp.platform.base;

import jp.mosp.framework.base.BaseBeanHandler;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ExportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportSearchBeanInterface;
import jp.mosp.platform.bean.file.HumanExportBeanInterface;
import jp.mosp.platform.bean.file.ImportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportSearchBeanInterface;
import jp.mosp.platform.bean.file.SectionExportBeanInterface;
import jp.mosp.platform.bean.file.UserExportBeanInterface;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageSearchBeanInterface;
import jp.mosp.platform.bean.portal.HolidayBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractSearchBeanInterface;
import jp.mosp.platform.bean.system.GeneralReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardSearchBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.NamingSearchBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionSearchBeanInterface;
import jp.mosp.platform.bean.system.ReceptionIcCardReferenceBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionSearchBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterSearchBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitSearchBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceSearchBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationSearchBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverSearchBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;

/**
 * 参照用BeanHandlerクラス。
 */
public class ReferenceBeanHandler extends BaseBeanHandler implements ReferenceBeanHandlerInterface {
	
	/**
	 * コンストラクタ。
	 */
	public ReferenceBeanHandler() {
		super();
	}
	
	@Override
	public UserMasterReferenceBeanInterface user() throws MospException {
		return (UserMasterReferenceBeanInterface)createBean(UserMasterReferenceBeanInterface.class);
	}
	
	@Override
	public SectionReferenceBeanInterface section() throws MospException {
		return (SectionReferenceBeanInterface)createBean(SectionReferenceBeanInterface.class);
	}
	
	@Override
	public PositionReferenceBeanInterface position() throws MospException {
		return (PositionReferenceBeanInterface)createBean(PositionReferenceBeanInterface.class);
	}
	
	@Override
	public EmploymentContractReferenceBeanInterface employmentContract() throws MospException {
		return (EmploymentContractReferenceBeanInterface)createBean(EmploymentContractReferenceBeanInterface.class);
	}
	
	@Override
	public NamingReferenceBeanInterface naming() throws MospException {
		return (NamingReferenceBeanInterface)createBean(NamingReferenceBeanInterface.class);
	}
	
	@Override
	public HumanSearchBeanInterface humanSearch() throws MospException {
		return (HumanSearchBeanInterface)createBean(HumanSearchBeanInterface.class);
	}
	
	@Override
	public HumanReferenceBeanInterface human() throws MospException {
		return (HumanReferenceBeanInterface)createBean(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public EntranceReferenceBeanInterface entrance() throws MospException {
		return (EntranceReferenceBeanInterface)createBean(EntranceReferenceBeanInterface.class);
	}
	
	@Override
	public SuspensionReferenceBeanInterface suspension() throws MospException {
		return (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
	}
	
	@Override
	public RetirementReferenceBeanInterface retirement() throws MospException {
		return (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
	}
	
	@Override
	public ConcurrentReferenceBeanInterface concurrent() throws MospException {
		return (ConcurrentReferenceBeanInterface)createBean(ConcurrentReferenceBeanInterface.class);
	}
	
	@Override
	public HumanHistoryReferenceBeanInterface humanHistory() throws MospException {
		return (HumanHistoryReferenceBeanInterface)createBean(HumanHistoryReferenceBeanInterface.class);
	}
	
	@Override
	public HumanArrayReferenceBeanInterface humanArray() throws MospException {
		return (HumanArrayReferenceBeanInterface)createBean(HumanArrayReferenceBeanInterface.class);
	}
	
	@Override
	public HumanNormalReferenceBeanInterface humanNormal() throws MospException {
		return (HumanNormalReferenceBeanInterface)createBean(HumanNormalReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryArrayReferenceBeanInterface humanBinaryArray() throws MospException {
		return (HumanBinaryArrayReferenceBeanInterface)createBean(HumanBinaryArrayReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryHistoryReferenceBeanInterface humanBinaryHistory() throws MospException {
		return (HumanBinaryHistoryReferenceBeanInterface)createBean(HumanBinaryHistoryReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryNormalReferenceBeanInterface humanBinaryNormal() throws MospException {
		return (HumanBinaryNormalReferenceBeanInterface)createBean(HumanBinaryNormalReferenceBeanInterface.class);
	}
	
	@Override
	public WorkPlaceReferenceBeanInterface workPlace() throws MospException {
		return (WorkPlaceReferenceBeanInterface)createBean(WorkPlaceReferenceBeanInterface.class);
	}
	
	@Override
	public WorkPlaceSearchBeanInterface workPlaceSearch() throws MospException {
		return (WorkPlaceSearchBeanInterface)createBean(WorkPlaceSearchBeanInterface.class);
	}
	
	@Override
	public RoleReferenceBeanInterface role() throws MospException {
		return (RoleReferenceBeanInterface)createBean(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public EmploymentContractSearchBeanInterface employmentContractSearch() throws MospException {
		return (EmploymentContractSearchBeanInterface)createBean(EmploymentContractSearchBeanInterface.class);
	}
	
	@Override
	public SectionSearchBeanInterface sectionSearch() throws MospException {
		return (SectionSearchBeanInterface)createBean(SectionSearchBeanInterface.class);
	}
	
	@Override
	public PositionSearchBeanInterface positionSearch() throws MospException {
		return (PositionSearchBeanInterface)createBean(PositionSearchBeanInterface.class);
	}
	
	@Override
	public NamingSearchBeanInterface namingSearch() throws MospException {
		return (NamingSearchBeanInterface)createBean(NamingSearchBeanInterface.class);
	}
	
	@Override
	public UserMasterSearchBeanInterface userMasterSearch() throws MospException {
		return (UserMasterSearchBeanInterface)createBean(UserMasterSearchBeanInterface.class);
	}
	
	@Override
	public WorkflowIntegrateBeanInterface workflowIntegrate() throws MospException {
		return (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public WorkflowReferenceBeanInterface workflow() throws MospException {
		return (WorkflowReferenceBeanInterface)createBean(WorkflowReferenceBeanInterface.class);
	}
	
	@Override
	public WorkflowCommentReferenceBeanInterface workflowComment() throws MospException {
		return (WorkflowCommentReferenceBeanInterface)createBean(WorkflowCommentReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalUnitReferenceBeanInterface approvalUnit() throws MospException {
		return (ApprovalUnitReferenceBeanInterface)createBean(ApprovalUnitReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalUnitSearchBeanInterface approvalUnitSearch() throws MospException {
		return (ApprovalUnitSearchBeanInterface)createBean(ApprovalUnitSearchBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteReferenceBeanInterface approvalRoute() throws MospException {
		return (ApprovalRouteReferenceBeanInterface)createBean(ApprovalRouteReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteSearchBeanInterface approvalRouteSearch() throws MospException {
		return (ApprovalRouteSearchBeanInterface)createBean(ApprovalRouteSearchBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteUnitReferenceBeanInterface approvalRouteUnit() throws MospException {
		return (ApprovalRouteUnitReferenceBeanInterface)createBean(ApprovalRouteUnitReferenceBeanInterface.class);
	}
	
	@Override
	public RouteApplicationReferenceBeanInterface routeApplication() throws MospException {
		return (RouteApplicationReferenceBeanInterface)createBean(RouteApplicationReferenceBeanInterface.class);
	}
	
	@Override
	public RouteApplicationSearchBeanInterface routeApplicationSearch() throws MospException {
		return (RouteApplicationSearchBeanInterface)createBean(RouteApplicationSearchBeanInterface.class);
	}
	
	@Override
	public RouteApplicationReferenceSearchBeanInterface routeApplicationReferenceSearch() throws MospException {
		return (RouteApplicationReferenceSearchBeanInterface)createBean(RouteApplicationReferenceSearchBeanInterface.class);
	}
	
	@Override
	public SubApproverReferenceBeanInterface subApprover() throws MospException {
		return (SubApproverReferenceBeanInterface)createBean(SubApproverReferenceBeanInterface.class);
	}
	
	@Override
	public SubApproverSearchBeanInterface subApproverSearch() throws MospException {
		return (SubApproverSearchBeanInterface)createBean(SubApproverSearchBeanInterface.class);
	}
	
	@Override
	public MessageReferenceBeanInterface message() throws MospException {
		return (MessageReferenceBeanInterface)createBean(MessageReferenceBeanInterface.class);
	}
	
	@Override
	public MessageSearchBeanInterface messageSearch() throws MospException {
		return (MessageSearchBeanInterface)createBean(MessageSearchBeanInterface.class);
	}
	
	@Override
	public ExportSearchBeanInterface exportSearch() throws MospException {
		return (ExportSearchBeanInterface)createBean(ExportSearchBeanInterface.class);
	}
	
	@Override
	public ExportReferenceBeanInterface export() throws MospException {
		return (ExportReferenceBeanInterface)createBean(ExportReferenceBeanInterface.class);
	}
	
	@Override
	public ExportFieldReferenceBeanInterface exportField() throws MospException {
		return (ExportFieldReferenceBeanInterface)createBean(ExportFieldReferenceBeanInterface.class);
	}
	
	@Override
	public ImportSearchBeanInterface importSearch() throws MospException {
		return (ImportSearchBeanInterface)createBean(ImportSearchBeanInterface.class);
	}
	
	@Override
	public ImportReferenceBeanInterface importRefer() throws MospException {
		return (ImportReferenceBeanInterface)createBean(ImportReferenceBeanInterface.class);
	}
	
	@Override
	public ImportFieldReferenceBeanInterface importField() throws MospException {
		return (ImportFieldReferenceBeanInterface)createBean(ImportFieldReferenceBeanInterface.class);
	}
	
	@Override
	public HumanExportBeanInterface humanExport() throws MospException {
		return (HumanExportBeanInterface)createBean(HumanExportBeanInterface.class);
	}
	
	@Override
	public UserExportBeanInterface userExport() throws MospException {
		return (UserExportBeanInterface)createBean(UserExportBeanInterface.class);
	}
	
	@Override
	public SectionExportBeanInterface sectionExport() throws MospException {
		return (SectionExportBeanInterface)createBean(SectionExportBeanInterface.class);
	}
	
	@Override
	public HolidayBeanInterface holiday() throws MospException {
		return (HolidayBeanInterface)createBean(HolidayBeanInterface.class);
	}
	
	@Override
	public GeneralReferenceBeanInterface generalReference() throws MospException {
		return (GeneralReferenceBeanInterface)createBean(GeneralReferenceBeanInterface.class);
	}
	
	@Override
	public IcCardReferenceBeanInterface icCard() throws MospException {
		return (IcCardReferenceBeanInterface)createBean(IcCardReferenceBeanInterface.class);
	}
	
	@Override
	public IcCardSearchBeanInterface icCardSearch() throws MospException {
		return (IcCardSearchBeanInterface)createBean(IcCardSearchBeanInterface.class);
	}
	
	@Override
	public ReceptionIcCardReferenceBeanInterface receptionIcCard() throws MospException {
		return (ReceptionIcCardReferenceBeanInterface)createBean(ReceptionIcCardReferenceBeanInterface.class);
	}
	
}
