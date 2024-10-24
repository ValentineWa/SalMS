package com.salms.salms.services;

import com.salms.salms.dto.AppointmentRequest;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.*;
import com.salms.salms.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class AppointmentService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private AppointmentDetailsRepository appointmentDetailsRepository;

    public Appointments booking (AppointmentRequest appointmentRequest){
        String phoneNumber = appointmentRequest.getPhoneNumber();

        //1. Look for existing customer by phone
       Customers customers =  customerRepository.findByPhoneNumber(appointmentRequest.getPhoneNumber());

        //2. If customer doesnt exist, create one
        if(customers == null){
            log.info("THIS IS A NEW CUSTOMER");

            customers = new Customers();
            customers.setId(UUID.randomUUID());
            customers.setFullName(appointmentRequest.getFullName());
            customers.setPhoneNumber(appointmentRequest.getPhoneNumber());
            customers.setStartDate(LocalDate.from(Instant.now()));
            customers.setCreationDate(Instant.now());
            customerRepository.save(customers);

            log.info("CUSTOMER DETAILS HAVE BEEN ADDED TO THE DATABASE SUCCESSFULY");
        }

        //4. Check if they have existing appointment

        Appointments booking = appointmentRepository.findByCustomersPhoneNumberAndAppDate(appointmentRequest.getPhoneNumber(), appointmentRequest.getAppDate());
            if (booking != null){

                log.info("CUSTOMER ALREADY HAS AN EXISTING APPOINTMENT IN THE SELECTED DATE %s", appointmentRequest.getAppDate());

        }

            //Create the appointment in the primary table
            booking = new Appointments();
            booking.setCustomers(customers);
            booking.setAppDate(appointmentRequest.getAppDate());
            booking.setTime(appointmentRequest.getTime());
            booking.setId(UUID.randomUUID());
            booking.setClientPreferences(appointmentRequest.getClientPreferences());
            booking.setAppStatus(Appointments.AppStatus.OPEN);
            booking.setCreatedOn(Instant.now());
            booking.setUpdatedOn(Instant.now());


        //5. Get the primary service getting done
        Set<Solutions> services = new HashSet<>();
        for (String serviceName : appointmentRequest.getServicesName()) {

            Solutions sol = solutionRepository.findByServiceName(serviceName);
            if (sol == null) {
                log.warn("SERVICE SELECTED NOT FOUND: {}", serviceName);
                continue;
            }
            services.add(sol);


            //3. Find the staff assigned to this service
            Staff staff = staffRepository.findByStaffAlias(appointmentRequest.getStaffAlias());

            if (staff == null) {
                log.warn("STAFF SELECTED IS NOT FOUND FOR THIS SERVICE: {}", serviceName);
                continue;

                // return the right error
                //cater how we'll handle walk ins
                //

            }

            //Cater for when its null: although its going to be a dropdown too not sure.


            //Create Appointment details
            AppointmentDetails details = new AppointmentDetails();
            details.setAppointments(booking);
            details.setServices(Collections.singleton(sol));
            details.setPrice(sol.getPrice());
            details.setDuration(sol.getDuration());
            details.setStaff(staff);
            booking.getAppointmentDetails().add(details);
        }
            appointmentRepository.save(booking);


            return booking;

            //5. Get the primary service getting done or get all and then distribute. But what if they add more later on?
            // I can add a statuses
            // Complete the appointment Booked
            // like if they actually came in its still open, Open
            // A status open till we confirm all services done. Confirm
            // Meaning before payment, we can edit an appointment to fill extra details eg all services and staff involved.


            //Cater for transition of status. complet when? Rescheduled when? Canceled when?
            //If the services are more than one who will that look like in my appointments
            //Are the services going to be stored as a list and how will I know who did what during an appointment?



        }



}