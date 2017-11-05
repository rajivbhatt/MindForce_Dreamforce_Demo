public class MuseNavigationDemoController{


    
    public List<Opportunity> opportunitySoLst {get;set;}
    public List<ProcessInstance> processInstanceSoLst {get;set;}
    private static final String MUSE_TOPIC_NAME = 'MuseNavigationDemo';
    public String progressBarMessage {get;set;}
    public String progressBarClass {get;set;}
    private Map<Decimal,String> progressBarClassMap {get;set;}
    private Map<Decimal,String> progressBarMessageMap  {get;set;}
    public Decimal progressBarValue {get;set;}
    public String channel{get; private set;}
    public Integer replayFrom{get;set;}
    public Id topicId{get;set;}
    private ProcessInstanceWorkitem  piWorkItem {get;set;}
    
    public MuseNavigationDemoController() {
        this.opportunitySoLst = [select id,name from Opportunity where Account.Name ='Bhatt Force' order by name desc];
        this.channel = '/topic/' + MUSE_TOPIC_NAME;
        this.replayFrom = -1;
        this.progressBarValue = 0;
        this.progressBarMessage = 'Focus...';
        this.progressBarClassMap = New Map<Decimal,String>();
        this.progressBarClassMap.put(5,'progress-bar progress-bar-danger');
        this.progressBarClassMap.put(10,'progress-bar progress-bar-danger');
        this.progressBarClassMap.put(25,'progress-bar progress-bar-warning');
        this.progressBarClassMap.put(50,'progress-bar progress-bar-warning');
        this.progressBarClassMap.put(75,'progress-bar progress-bar-info');
        this.progressBarClassMap.put(90,'progress-bar progress-bar-info');
        this.progressBarClassMap.put(100,'progress-bar progress-bar-success');
        this.progressBarMessageMap = New Map<Decimal,String>();
        this.progressBarMessageMap.put(5,'Calibration Done...');
        this.progressBarMessageMap.put(10,'Good Start...');
        this.progressBarMessageMap.put(25,'Keep Focusing...');
        this.progressBarMessageMap.put(50,'You can do this...');
        this.progressBarMessageMap.put(75,'Almost There...');                
        this.progressBarMessageMap.put(90,'This is Nirvana...');                
        this.progressBarMessageMap.put(100,'Magic of Mindfulness! Record Approved...');            
        this.progressBarClass = 'progress-bar progress-bar-danger';    

   this.processInstanceSoLst =  [Select Id,  TargetObjectId, isDeleted, Status, (
                                                                Select Id, ProcessInstanceId, ActorId, Actor.Name, StepStatus, Comments
                                                                From StepsAndWorkItems
                                                                Where
                                                                isDeleted = false and StepStatus = 'Pending'
                                                                Order By
                                                                Createddate Desc

                                                               )
                From ProcessInstance
                Where
                    isDeleted = false and Status = 'Pending'
                    Order By
                    Createddate Desc limit 3] ;
        
        for(ProcessInstance pi : processInstanceSoLst ){
            if(pi.targetObjectId==opportunitySoLst[0].id) {
               this.piWorkItem = [select id from ProcessInstanceWorkitem where ProcessInstanceId = :pi.id];
               break;
            }
         }
    }
    
    public PageReference getOrCreatePushChannel() {
        List<PushTopic> pushTopics = [SELECT Id,Name FROM PushTopic WHERE Name=:MUSE_TOPIC_NAME];

        if (null == pushTopics || pushTopics.size() == 0) {
            PushTopic topic = new PushTopic();
            topic.Name = MUSE_TOPIC_NAME;
            topic.Query = 'SELECT Id,user_action__c FROM Brain_Signal_Tracker__c';
            topic.NotifyForOperationUpdate = true;
            topic.NotifyForFields = 'All';
            topic.ApiVersion = 37.0;
            insert topic;
            this.topicId = topic.Id;
        } else {
            this.topicId = pushTopics.get(0).Id;
        }

        return null;
    }
    


 public void refreshConcentrationProgress() {
    
    
   String recordId =  ApexPages.currentPage().getParameters().get('RecordId');
   system.debug('recordId = ' + recordid);
    Brain_Signal_Tracker__c bstSo = [select concentration_level__c from Brain_Signal_Tracker__c where id = :recordId limit 1];
   this.progressBarValue = bstSo.concentration_level__c;
   
    this.progressBarMessage = this.progressBarMessageMap.get(bstSo.concentration_level__c);
    this.progressBarClass = this.progressBarClassMap.get(bstSo.concentration_level__c); 
    if(bstSo.concentration_level__c==100)
    {
        Approval.ProcessWorkitemRequest req = 
            new Approval.ProcessWorkitemRequest();
        req.setComments('Approving request.');
        req.setAction('Approve');
        

        // Use the ID from the newly created item to specify the item to be worked
        req.setWorkitemId(this.piWorkItem.id);
        
        // Submit the request for approval
        Approval.ProcessResult result2 =  Approval.process(req);
        

       this.processInstanceSoLst =  [Select Id,  TargetObjectId, isDeleted, Status, (
                                                                Select Id, ProcessInstanceId, ActorId, Actor.Name, StepStatus, Comments
                                                                From StepsAndWorkItems
                                                                Where
                                                                isDeleted = false
                                                                and StepStatus='Approved'
                                                                Order By
                                                                Createddate Desc

                                                               )
                From ProcessInstance
                Where
                    isDeleted = false 
                    Order By
                    Createddate Desc limit 1] ;
       
                    
       }


    }
    
       
    
}