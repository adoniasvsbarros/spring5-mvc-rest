package guru.springfamework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	CategoryRepository categoryRepository;
	CustomerRepository customerRepository;
	VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
			VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadCategories();
		loadCustomers();
		loadVendors();
	}

	public void loadCategories() {
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);

		log.info("Data " + categoryRepository.count() + " categories loaded");
	}

	public void loadCustomers() {
		Customer susan = new Customer();
		susan.setFirstname("Susan");
		susan.setLastname("Boyle");

		Customer charlie = new Customer();
		charlie.setFirstname("Charlie");
		charlie.setLastname("Frankstain");

		Customer matt = new Customer();
		matt.setFirstname("Matt");
		matt.setLastname("Simons");

		customerRepository.save(susan);
		customerRepository.save(charlie);
		customerRepository.save(matt);

		log.info("Data " + customerRepository.count() + " customers loaded");
	}

	public void loadVendors() {
		Vendor carrefour = new Vendor();
		carrefour.setName("Carrefour");

		Vendor maxx = new Vendor();
		maxx.setName("Maxx shop");

		vendorRepository.save(carrefour);
		vendorRepository.save(maxx);

		log.info("Data " + vendorRepository.count() + " vendors loaded");

	}

}
