@RestResource(urlMapping='/concentration')
global class MuseSalesforceDemoService{
    @HttpGet
    global static void doGet() {
        String concentrationLevelDataFromMuse= RestContext.request.params.get('level');
        Brain_Signal_Tracker__c bstSo = [select concentration_level__c from Brain_Signal_Tracker__c where user_action__c = 'Concentration' limit 1];
        bstSo.concentration_level__c = Decimal.ValueOf(concentrationLevelDataFromMuse);
        update bstSo;
    }
}