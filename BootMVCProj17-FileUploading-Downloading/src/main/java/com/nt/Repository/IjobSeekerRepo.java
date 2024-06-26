package com.nt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.entity.JobSeekerInfo;

public interface IjobSeekerRepo extends JpaRepository<JobSeekerInfo, Integer> {
@Query("select resumePath from JobSeekerInfo where jsId=:id")
	public String getResumePathByJsId(Integer id);

@Query("select photoPath from JobSeekerInfo where jsId=:id")
public String getPhotoPathByJsId(Integer id);
}
