@RestResource(urlMapping='/NavigationDemo')
global class MuseSalesforceNavigationDemoService{
    @HttpGet
    global static void doGet() {
        String directionDataFromMuseStr= RestContext.request.params.get('direction');
        Brain_Signal_Tracker__c bstSo = [select user_action__c from Brain_Signal_Tracker__c where name = 'NavigationDemo' limit 1];
        bstSo.user_action__c = directionDataFromMuseStr;
        update bstSo;
    }
}