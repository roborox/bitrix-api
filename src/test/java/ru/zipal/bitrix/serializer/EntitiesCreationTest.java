package ru.zipal.bitrix.serializer;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.zipal.bitrix.api.model.BitrixActivity;
import ru.zipal.bitrix.api.model.BitrixCompany;
import ru.zipal.bitrix.api.model.BitrixContact;
import ru.zipal.bitrix.api.model.BitrixLead;
import ru.zipal.bitrix.api.model.enums.YesNo;

public class EntitiesCreationTest {
    @Test
    public void test() {
        BitrixCompany company = new BitrixCompany();
        Assert.assertNotNull(company.getPhones());
        Assert.assertNotNull(company.getEmails());

        BitrixActivity activity = new BitrixActivity();
        Assert.assertNotNull(activity.getCommunications());

        BitrixContact contact = new BitrixContact();
        Assert.assertNotNull(contact.getPhones());
        Assert.assertNotNull(contact.getEmails());

        BitrixLead lead = new BitrixLead();
        Assert.assertNotNull(lead.getPhones());
        Assert.assertNotNull(lead.getEmails());
        Assert.assertEquals(YesNo.YES, lead.getOpened());
    }
}
