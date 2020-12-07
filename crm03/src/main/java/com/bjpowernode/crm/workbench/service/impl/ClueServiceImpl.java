package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 动力节点老崔
 * 2019/12/16
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao;

    @Autowired
    private ClueRemarkDao clueRemarkDao;

    @Autowired
    private ClueActivityRelationDao carDao;

    @Autowired
    private ContactsDao contactsDao;

    @Autowired
    private ContactsRemarkDao contactsRemarkDao;

    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    @Autowired
    private TranDao tranDao;

    @Autowired
    private TranHistoryDao tranHistoryDao;


    @Override
    public void saveClue(Clue clue) {
        clueDao.saveClue(clue);
    }

    @Override
    public Clue getClueByIdForOwner(String id) {
        Clue c=clueDao.getClueByIdForOwner(id);
        return c;
    }

    @Override
    public void deleteRelation(String carId) {
        carDao.deleteRelation(carId);
    }

    @Override
    public void saveRelation(String clueId, String[] activityIds) {
        List<ClueActivityRelation> carList=new ArrayList<>();

       for(String activityId:activityIds){
           ClueActivityRelation car=new ClueActivityRelation();
           car.setId(UUIDUtil.getUUID());
           car.setActivityId(activityId);
           car.setClueId(clueId);
           carList.add(car);
       }

       carDao.saveRelation(carList);
    }

    @Override
    public void convert(String flag, Tran t, String createBy, String clueId) {
        Clue c=clueDao.getClueById(clueId);
        String company = c.getCompany();

        //将线索中的信息加入到联系人表和客户表中
        Customer cust=customerDao.getCustomerNameByName(company);
        if(cust==null){
            cust=new Customer();
            cust.setId(UUIDUtil.getUUID());
            cust.setAddress(c.getAddress());
            cust.setCreateBy(createBy);
            cust.setCreateTime(DateTimeUtil.getSysTime());
            cust.setWebsite(c.getWebsite());
            cust.setPhone(c.getPhone());
            cust.setNextContactTime(c.getNextContactTime());
            cust.setContactSummary(c.getContactSummary());
            cust.setOwner(c.getOwner());
            cust.setDescription(c.getDescription());
            cust.setName(c.getCompany());
            customerDao.saveCustomer(cust);
        }


        Contacts con=new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setMphone(c.getMphone());
        con.setEmail(c.getEmail());
        con.setJob(c.getJob());
        con.setFullname(c.getFullname());
        con.setOwner(c.getOwner());
        con.setCustomerId(cust.getId());
        con.setAppellation(c.getAppellation());
        con.setSource(c.getSource());
        con.setAddress(c.getAddress());
        con.setContactSummary(c.getContactSummary());
        con.setDescription(c.getDescription());
        con.setNextContactTime(c.getNextContactTime());
        con.setCreateTime(DateTimeUtil.getSysTime());
        con.setCreateBy(createBy);

        contactsDao.saveContacts(con);

        //将线索备注中的信息加入到联系人和客户备注表中
        List<ClueRemark> clueRemarkList= clueRemarkDao.getRemarkByClueId(clueId);

        List<CustomerRemark> customerRemarkList=new ArrayList<>();

        List<ContactsRemark> contactsRemarkList=new ArrayList<>();

        for(ClueRemark clueRemark:clueRemarkList){
            String noteContent = clueRemark.getNoteContent();
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setCustomerId(cust.getId());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setEditFlag("0");
            customerRemarkList.add(customerRemark);

            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setEditFlag("0");
            contactsRemarkList.add(contactsRemark);
        }

        customerRemarkDao.saveCustomerRemark(customerRemarkList);

        contactsRemarkDao.saveContactsRemark(contactsRemarkList);


        //将线索关系表加入到联系人关系表中
        List<ClueActivityRelation> clueActivityRelationList=carDao.getClueActivityRelationByClueId(clueId);
        for(ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            String activityId = clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());
            contactsActivityRelationDao.saveContactsActivityRelation(contactsActivityRelation);
        }



        if("a".equals(flag)){

            /*

                控制中已经为t对象封装了基础值
                money,name,stage,expectedDate,activityId,id,createBy,createTime

                交易对象t中，其他的信息根据用户的需求，可以先不填，也可以从以上获取的信息中得到


             */

            t.setId(UUIDUtil.getUUID());
            t.setCreateBy(createBy);
            t.setCreateTime(DateTimeUtil.getSysTime());
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setDescription(c.getDescription());
            t.setCustomerId(cust.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(con.getId());

            //添加交易
            tranDao.saveTran(t);

            /*

                7.如果我们创建了一条交易，应该伴随着生成一条交易历史
                    //添加交易历史应该紧跟在添加交易后面，不要出if语句块

             */
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());

            //添加交易历史
            tranHistoryDao.saveTranHistory(tranHistory);


        }


      /*  clueDao.deleteClueById(clueId);

        clueRemarkDao.deleteClueRemarkById(clueId);

        carDao.deleteRelationByClueId(clueId);*/




    }

}
