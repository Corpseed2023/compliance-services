//package com.lawzoom.complianceservice.serviceImpl;
//
//import com.lawzoom.complianceservice.model.Ticket;
//import com.lawzoom.complianceservice.payload.request.TicketRequest;
//import com.lawzoom.complianceservice.payload.response.TicketResponse;
//import com.lawzoom.complianceservice.repository.TicketRepository;
//import com.lawzoom.complianceservice.service.TicketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//
//@Service
//public class TicketServiceImpl implements TicketService {
//
//
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    @Override
//    public TicketResponse raiseTicket(TicketRequest ticketRequest) {
//        Ticket ticket = new Ticket();
//        ticket.setSubject(ticketRequest.getSubject());
//        ticket.setDescription(ticketRequest.getDescription());
//
//        Ticket savedTicket = ticketRepository.save(ticket);
//
//        return new TicketResponse(
//                savedTicket.getId(),
//                savedTicket.getCreationDate(),
//                savedTicket.getSubject(),
//                savedTicket.getDescription(),
//                savedTicket.isStatus()
//        );
//    }
//
//    @Override
//    public List<TicketResponse> getAllTickets() {
//        List<Ticket> allTickets = ticketRepository.findAll();
//        return allTickets.stream()
//                .map(ticket -> new TicketResponse(
//                        ticket.getId(),
//                        ticket.getCreationDate(),
//                        ticket.getSubject(),
//                        ticket.getDescription(),
//                        ticket.isStatus()
//                ))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public TicketResponse getTicketById(Long id) {
//        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
//
//        if (optionalTicket.isPresent()) {
//            Ticket ticket = optionalTicket.get();
//            return new TicketResponse(
//                    ticket.getId(),
//                    ticket.getCreationDate(),
//                    ticket.getSubject(),
//                    ticket.getDescription(),
//                    ticket.isStatus()
//            );
//        } else {
//            return null;
//        }
//    }
//
//
//    @Override
//    public TicketResponse updateTicket(Long id, TicketRequest ticketUpdateRequest) {
//        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
//
//        if (optionalTicket.isPresent()) {
//            Ticket existingTicket = optionalTicket.get();
//
//            // Update the fields based on the request
//            existingTicket.setSubject(ticketUpdateRequest.getSubject());
//            existingTicket.setDescription(ticketUpdateRequest.getDescription());
//
//            Ticket updatedTicket = ticketRepository.save(existingTicket);
//
//            return new TicketResponse(
//                    updatedTicket.getId(),
//                    updatedTicket.getCreationDate(),
//                    updatedTicket.getSubject(),
//                    updatedTicket.getDescription(),
//                    updatedTicket.isStatus()
//            );
//        } else {
//            return null;
//        }
//
//    }
//
//
//    @Override
//    public boolean deleteTicket(Long id) {
//        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
//
//        if (optionalTicket.isPresent()) {
//            ticketRepository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }
//    }
//}
