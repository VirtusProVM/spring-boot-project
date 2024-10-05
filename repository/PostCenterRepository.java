package com.jobportal.repository;

import com.jobportal.helper.RecruiterHelper;
import com.jobportal.model.PostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostCenterRepository extends JpaRepository<PostCenter, Integer> {

    @Query(value = " SELECT COUNT(s.userID) AS totalCandidates,p.postID,p.job_title,l.locationID as locationID,l.city,l.state," +
            "l.country,c.companyID as companyID,c.company_name FROM post_center p " +
            " inner join location l " +
            " on p.locationID = l.locationID " +
            " INNER join company c  " +
            " on p.companyID = c.companyID " +
            " left join job_apply s " +
            " on s.job = p.postID " +
            " where p.posted_byid = :recruiter " +
            " GROUP By p.postID" ,nativeQuery = true)
    List<RecruiterHelper> getRecruiterJobs(@Param("recruiter") int recruiter);

    @Query(value = "SELECT * FROM post_center p INNER JOIN location l ON p.locationID=l.locationID  WHERE p" +
            ".jobTitle LIKE %:job%"
            + " AND (l.city LIKE %:location%"
            + " OR l.country LIKE %:location%"
            + " OR l.state LIKE %:location%) " +
            " AND (j.jobType IN(:type)) " +
            " AND (j.remote IN(:remote)) ", nativeQuery = true)
    List<PostCenter> searchJobsWithoutDate(@Param("job") String job,
                                           @Param("location") String location,
                                           @Param("remote") List<String> remote,
                                           @Param("type") List<String> type);

    @Query(value = "SELECT * FROM post_center p INNER JOIN location l on j.locationID=l.locationID  WHERE p" +
            ".jobTitle LIKE %:job%"
            + " AND (l.city LIKE %:location%"
            + " OR l.country LIKE %:location%"
            + " OR l.state LIKE %:location%) " +
            " AND (j.jobType IN(:type)) " +
            " AND (j.remote IN(:remote)) " +
            " AND (postedDate >= :date)", nativeQuery = true)
    List<PostCenter> searchJobs(@Param("job") String job,
                                @Param("location") String location,
                                @Param("remote") List<String> remote,
                                @Param("type") List<String> type,
                                @Param("date") LocalDate searchDate);
}
