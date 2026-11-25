package com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long> {

  @Query(
      """
      select e from EmployeeEntity e
      where e.id > :afterId and (:includeInactive = true or e.active = true)
      order by e.id asc
      """)
  List<EmployeeEntity> findPage(
      @Param("afterId") long afterId,
      @Param("includeInactive") boolean includeInactive,
      Pageable pageable);

  @Query(
      """
      select e from EmployeeEntity e
      where e.id > :afterId and (:includeInactive = true or e.active = true)
        and (lower(e.firstName) like :pattern escape '!'
          or lower(e.middleName) like :pattern escape '!'
          or lower(e.paternalSurname) like :pattern escape '!'
          or lower(e.maternalSurname) like :pattern escape '!')
      order by e.id asc
      """)
  List<EmployeeEntity> searchPage(
      @Param("pattern") String pattern,
      @Param("afterId") long afterId,
      @Param("includeInactive") boolean includeInactive,
      Pageable pageable);

  List<EmployeeEntity> findByFirstNameContains(String firstName);
}
