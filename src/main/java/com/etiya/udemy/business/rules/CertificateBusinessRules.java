package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.CertificateRepository;
import com.etiya.udemy.dataAccess.abstracts.UserCourseAssignmentRepository;
import com.etiya.udemy.entities.concretes.Certificate;
import org.springframework.stereotype.Service;

@Service
public class CertificateBusinessRules {

    private final CertificateRepository certificateRepository;
    private final UserCourseAssignmentRepository assignmentRepository;

    public CertificateBusinessRules(CertificateRepository certificateRepository,
                                    UserCourseAssignmentRepository assignmentRepository) {
        this.certificateRepository = certificateRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public Certificate certificateMustExist(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.Certificate.NOT_FOUND));
    }

    public void userMustBeEnrolledToGetCertificate(Long userId, Long courseId) {
        if (!assignmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Certificate.MUST_BE_ENROLLED);
        }
    }

    public void certificateCannotBeIssuedTwice(Long userId, Long courseId) {
        if (certificateRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Certificate.ALREADY_ISSUED);
        }
    }
}
