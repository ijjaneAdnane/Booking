package com.Boujorno.adnanemanager.service.interfac;

import com.Boujorno.adnanemanager.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    Response addNewRoom(String roomPhotoUrl, String roomType, BigDecimal roomPrice, String description);
    Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, String roomPhotoUrl);
    List<String> getAllRoomTypes();
    Response getAllRooms();
    Response getRoomById(Long roomId);
    Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
    Response getAllAvailableRooms();
    Response deleteRoom(Long roomId);

}
