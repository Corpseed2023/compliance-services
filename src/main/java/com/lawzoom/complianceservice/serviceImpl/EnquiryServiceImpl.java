package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.model.Enquiry;
import com.lawzoom.complianceservice.repository.EnquiryRepo;
import com.lawzoom.complianceservice.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private EnquiryRepo enquiryRepo;
    @Override
    public Enquiry createEnquiry(Enquiry enquiry) {

        Enquiry enquiry1 = this.enquiryRepo.findByMobile(enquiry.getMobile());

        if (enquiry1!=null)
        {
            enquiry1.setCount(enquiry1.getCount()+1);
            return enquiryRepo.save(enquiry1);
        }
       else
        {
            return enquiryRepo.save(enquiry);
        }

    }
}
