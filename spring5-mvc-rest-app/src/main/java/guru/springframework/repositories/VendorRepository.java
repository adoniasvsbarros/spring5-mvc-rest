package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
