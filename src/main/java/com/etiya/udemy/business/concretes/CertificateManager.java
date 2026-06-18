package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.CertificateService;
import com.etiya.udemy.business.dtos.requests.certificate.CreateCertificateRequest;
import com.etiya.udemy.business.dtos.responses.certificate.CertificateResponse;
import com.etiya.udemy.business.mappers.CertificateMapper;
import com.etiya.udemy.business.rules.CertificateBusinessRules;
import com.etiya.udemy.business.rules.CourseBusinessRules;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.CertificateRepository;
import com.etiya.udemy.entities.concretes.Certificate;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CertificateManager implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final CertificateBusinessRules rules;
    private final UserBusinessRules userBusinessRules;
    private final CourseBusinessRules courseBusinessRules;

    public CertificateManager(CertificateRepository certificateRepository,
                              CertificateMapper certificateMapper,
                              CertificateBusinessRules rules,
                              UserBusinessRules userBusinessRules,
                              CourseBusinessRules courseBusinessRules) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.rules = rules;
        this.userBusinessRules = userBusinessRules;
        this.courseBusinessRules = courseBusinessRules;
    }

    @Override
    public CertificateResponse issue(CreateCertificateRequest request) {
        User user = userBusinessRules.userMustExist(request.getUserId());
        Course course = courseBusinessRules.courseMustExist(request.getCourseId());

        rules.userMustBeEnrolledToGetCertificate(request.getUserId(), request.getCourseId());
        rules.certificateCannotBeIssuedTwice(request.getUserId(), request.getCourseId());

        Certificate certificate = new Certificate();
        certificate.setUser(user);
        certificate.setCourse(course);
        certificate.setCertificateNo(generateCertificateNo());
        certificate.setIssueDate(LocalDate.now());

        return certificateMapper.toResponse(certificateRepository.save(certificate));
    }

    @Override
    public CertificateResponse getById(Long id) {
        return certificateMapper.toResponse(rules.certificateMustExist(id));
    }

    @Override
    public List<CertificateResponse> getAll() {
        return certificateMapper.toResponseList(certificateRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        Certificate certificate = rules.certificateMustExist(id);
        certificate.setActive(false);
        certificate.setDeletedDate(LocalDateTime.now());
        certificateRepository.save(certificate);
    }

    private String generateCertificateNo() {
        String certificateNo;
        do {
            certificateNo = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (certificateRepository.existsByCertificateNo(certificateNo));
        return certificateNo;
    }
}
