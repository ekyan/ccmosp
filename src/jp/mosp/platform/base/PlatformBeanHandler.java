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
import jp.mosp.platform.bean.file.ExportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ExportRegistBeanInterface;
import jp.mosp.platform.bean.file.HumanImportBeanInterface;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.bean.file.ImportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ImportRegistBeanInterface;
import jp.mosp.platform.bean.file.SectionImportBeanInterface;
import jp.mosp.platform.bean.file.UserImportBeanInterface;
import jp.mosp.platform.bean.file.UserPasswordImportBeanInterface;
import jp.mosp.platform.bean.file.impl.UnitPersonImportBean;
import jp.mosp.platform.bean.file.impl.UnitSectionImportBean;
import jp.mosp.platform.bean.human.ConcurrentRegistBeanInterface;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.HistoryBasicDeleteBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.human.RetirementRegistBeanInterface;
import jp.mosp.platform.bean.human.SuspensionRegistBeanInterface;
import jp.mosp.platform.bean.message.MessageRegistBeanInterface;
import jp.mosp.platform.bean.portal.AuthBeanInterface;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractRegistBeanInterface;
import jp.mosp.platform.bean.system.GeneralRegistBeanInterface;
import jp.mosp.platform.bean.system.IcCardRegistBeanInterface;
import jp.mosp.platform.bean.system.NamingRegistBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterCheckBeanInterface;
import jp.mosp.platform.bean.system.PositionRegistBeanInterface;
import jp.mosp.platform.bean.system.ReceptionIcCardRegistBeanInterface;
import jp.mosp.platform.bean.system.SectionRegistBeanInterface;
import jp.mosp.platform.bean.system.UserMasterRegistBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitRegistBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationRegistBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;

/**
 * MosPプラットフォーム用BeanHandler。<br>
 * <br>
 */
public class PlatformBeanHandler extends BaseBeanHandler implements PlatformBeanHandlerInterface {
	
	/**
	 * コンストラクタ。
	 */
	public PlatformBeanHandler() {
		super();
	}
	
	@Override
	public AuthBeanInterface auth() throws MospException {
		return (AuthBeanInterface)createBean(AuthBeanInterface.class);
	}
	
	@Override
	public UserCheckBeanInterface userCheck() throws MospException {
		return (UserCheckBeanInterface)createBean(UserCheckBeanInterface.class);
	}
	
	@Override
	public PasswordCheckBeanInterface passwordCheck() throws MospException {
		return (PasswordCheckBeanInterface)createBean(PasswordCheckBeanInterface.class);
	}
	
	@Override
	public PlatformMasterCheckBeanInterface masterCheck() throws MospException {
		return (PlatformMasterCheckBeanInterface)createBean(PlatformMasterCheckBeanInterface.class);
	}
	
	@Override
	public UserPasswordRegistBeanInterface userPasswordRegist() throws MospException {
		return (UserPasswordRegistBeanInterface)createBean(UserPasswordRegistBeanInterface.class);
	}
	
	@Override
	public UserMasterRegistBeanInterface userMasterRegist() throws MospException {
		return (UserMasterRegistBeanInterface)createBean(UserMasterRegistBeanInterface.class);
	}
	
	@Override
	public EmploymentContractRegistBeanInterface employmentContractRegist() throws MospException {
		return (EmploymentContractRegistBeanInterface)createBean(EmploymentContractRegistBeanInterface.class);
	}
	
	@Override
	public SectionRegistBeanInterface sectionRegist() throws MospException {
		return (SectionRegistBeanInterface)createBean(SectionRegistBeanInterface.class);
	}
	
	@Override
	public WorkPlaceRegistBeanInterface workPlaceRegist() throws MospException {
		return (WorkPlaceRegistBeanInterface)createBean(WorkPlaceRegistBeanInterface.class);
	}
	
	@Override
	public PositionRegistBeanInterface positionRegist() throws MospException {
		return (PositionRegistBeanInterface)createBean(PositionRegistBeanInterface.class);
	}
	
	@Override
	public NamingRegistBeanInterface namingRegist() throws MospException {
		return (NamingRegistBeanInterface)createBean(NamingRegistBeanInterface.class);
	}
	
	@Override
	public HumanRegistBeanInterface humanRegist() throws MospException {
		return (HumanRegistBeanInterface)createBean(HumanRegistBeanInterface.class);
	}
	
	@Override
	public HistoryBasicDeleteBeanInterface historyBasicDelete() throws MospException {
		return (HistoryBasicDeleteBeanInterface)createBean(HistoryBasicDeleteBeanInterface.class);
	}
	
	@Override
	public EntranceRegistBeanInterface entranceRegist() throws MospException {
		return (EntranceRegistBeanInterface)createBean(EntranceRegistBeanInterface.class);
	}
	
	@Override
	public RetirementRegistBeanInterface retirementRegist() throws MospException {
		return (RetirementRegistBeanInterface)createBean(RetirementRegistBeanInterface.class);
	}
	
	@Override
	public SuspensionRegistBeanInterface suspensionRegist() throws MospException {
		return (SuspensionRegistBeanInterface)createBean(SuspensionRegistBeanInterface.class);
	}
	
	@Override
	public ConcurrentRegistBeanInterface concurrentRegist() throws MospException {
		return (ConcurrentRegistBeanInterface)createBean(ConcurrentRegistBeanInterface.class);
	}
	
	@Override
	public IcCardRegistBeanInterface icCardRegist() throws MospException {
		return (IcCardRegistBeanInterface)createBean(IcCardRegistBeanInterface.class);
	}
	
	@Override
	public ReceptionIcCardRegistBeanInterface receptionIcCardRegist() throws MospException {
		return (ReceptionIcCardRegistBeanInterface)createBean(ReceptionIcCardRegistBeanInterface.class);
	}
	
	@Override
	public HumanHistoryRegistBeanInterface humanHistoryRegist() throws MospException {
		return (HumanHistoryRegistBeanInterface)createBean(HumanHistoryRegistBeanInterface.class);
	}
	
	@Override
	public HumanArrayRegistBeanInterface humanArrayRegist() throws MospException {
		return (HumanArrayRegistBeanInterface)createBean(HumanArrayRegistBeanInterface.class);
	}
	
	@Override
	public HumanNormalRegistBeanInterface humanNormalRegist() throws MospException {
		return (HumanNormalRegistBeanInterface)createBean(HumanNormalRegistBeanInterface.class);
	}
	
	@Override
	public HumanBinaryArrayRegistBeanInterface humanBinaryArrayRegist() throws MospException {
		return (HumanBinaryArrayRegistBeanInterface)createBean(HumanBinaryArrayRegistBeanInterface.class);
	}
	
	@Override
	public HumanBinaryHistoryRegistBeanInterface humanBinaryHistoryRegist() throws MospException {
		return (HumanBinaryHistoryRegistBeanInterface)createBean(HumanBinaryHistoryRegistBeanInterface.class);
	}
	
	@Override
	public HumanBinaryNormalRegistBeanInterface humanBinaryNormalRegist() throws MospException {
		return (HumanBinaryNormalRegistBeanInterface)createBean(HumanBinaryNormalRegistBeanInterface.class);
	}
	
	@Override
	public HistoryBasicDeleteBeanInterface historyBasicDelete(String className) throws MospException {
		return (HistoryBasicDeleteBeanInterface)createBean(className);
	}
	
	@Override
	public WorkflowRegistBeanInterface workflowRegist() throws MospException {
		return (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
	}
	
	@Override
	public WorkflowCommentRegistBeanInterface workflowCommentRegist() throws MospException {
		return (WorkflowCommentRegistBeanInterface)createBean(WorkflowCommentRegistBeanInterface.class);
	}
	
	@Override
	public ApprovalUnitRegistBeanInterface approvalUnitRegist() throws MospException {
		return (ApprovalUnitRegistBeanInterface)createBean(ApprovalUnitRegistBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteRegistBeanInterface approvalRouteRegist() throws MospException {
		return (ApprovalRouteRegistBeanInterface)createBean(ApprovalRouteRegistBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteUnitRegistBeanInterface approvalRouteUnitRegist() throws MospException {
		return (ApprovalRouteUnitRegistBeanInterface)createBean(ApprovalRouteUnitRegistBeanInterface.class);
	}
	
	@Override
	public RouteApplicationRegistBeanInterface routeApplicationRegist() throws MospException {
		return (RouteApplicationRegistBeanInterface)createBean(RouteApplicationRegistBeanInterface.class);
	}
	
	@Override
	public SubApproverRegistBeanInterface subApproverRegist() throws MospException {
		return (SubApproverRegistBeanInterface)createBean(SubApproverRegistBeanInterface.class);
	}
	
	@Override
	public MessageRegistBeanInterface messageRegist() throws MospException {
		return (MessageRegistBeanInterface)createBean(MessageRegistBeanInterface.class);
	}
	
	@Override
	public GeneralRegistBeanInterface generalRegist() throws MospException {
		return (GeneralRegistBeanInterface)createBean(GeneralRegistBeanInterface.class);
	}
	
	@Override
	public ExportRegistBeanInterface exportRegist() throws MospException {
		return (ExportRegistBeanInterface)createBean(ExportRegistBeanInterface.class);
	}
	
	@Override
	public ExportFieldRegistBeanInterface exportFieldRegist() throws MospException {
		return (ExportFieldRegistBeanInterface)createBean(ExportFieldRegistBeanInterface.class);
	}
	
	@Override
	public ImportRegistBeanInterface importRegist() throws MospException {
		return (ImportRegistBeanInterface)createBean(ImportRegistBeanInterface.class);
	}
	
	@Override
	public ImportFieldRegistBeanInterface importFieldRegist() throws MospException {
		return (ImportFieldRegistBeanInterface)createBean(ImportFieldRegistBeanInterface.class);
	}
	
	@Override
	public HumanImportBeanInterface humanImport() throws MospException {
		return (HumanImportBeanInterface)createBean(HumanImportBeanInterface.class);
	}
	
	@Override
	public UserImportBeanInterface userImport() throws MospException {
		return (UserImportBeanInterface)createBean(UserImportBeanInterface.class);
	}
	
	@Override
	public SectionImportBeanInterface sectionImport() throws MospException {
		return (SectionImportBeanInterface)createBean(SectionImportBeanInterface.class);
	}
	
	@Override
	public ImportBeanInterface unitSectionImport() throws MospException {
		return (ImportBeanInterface)createBean(UnitSectionImportBean.class);
	}
	
	@Override
	public ImportBeanInterface unitPersonImport() throws MospException {
		return (ImportBeanInterface)createBean(UnitPersonImportBean.class);
	}
	
	@Override
	public UserPasswordImportBeanInterface userPasswordImport() throws MospException {
		return (UserPasswordImportBeanInterface)createBean(UserPasswordImportBeanInterface.class);
	}
	
	@Override
	public PortalBeanInterface portal(String className) throws MospException {
		return (PortalBeanInterface)createBean(className);
	}
	
}
