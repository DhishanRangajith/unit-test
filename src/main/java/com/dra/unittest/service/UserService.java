package com.dra.unittest.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.dra.unittest.DTO.UserDTO;
import com.dra.unittest.entity.UserEntity;
import com.dra.unittest.exception.NotFoundException;
import com.dra.unittest.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDTO save(UserDTO userDTO){
        UserEntity userEntity = new UserEntity(userDTO.getName(), userDTO.getEmail());
        UserEntity savedUserEntity = this.userRepository.save(userEntity);
        UserDTO savedUserDTO = new UserDTO(savedUserEntity.getId(), savedUserEntity.getName(), savedUserEntity.getEmail());
        return savedUserDTO;
    }

    public List<UserDTO> getAll(){
        List<UserEntity> entityList= this.userRepository.findAll();
        List<UserDTO> dtoList = entityList
                                    .stream()
                                    .parallel()
                                    .map(x -> new UserDTO(x.getId(), x.getName(), x.getEmail()))
                                    .collect(Collectors.toList());
        return dtoList;
    }

    public UserDTO get(Long id){
        Optional<UserEntity> entityList= this.userRepository.findById(id);
        UserEntity entity = entityList.orElseThrow(() -> new NotFoundException("User not found"));
        UserDTO dto = new UserDTO(entity.getId(), entity.getName(), entity.getEmail());
        return dto;
    }

    public UserDTO update(Long id, UserDTO userDTO){
        Optional<UserEntity> existingEntityOpt = this.userRepository.findById(id);
        existingEntityOpt.orElseThrow(() -> new NotFoundException("User not found"));

        UserEntity userEntity = new UserEntity(id, userDTO.getName(), userDTO.getEmail());
        UserEntity updatedEntity = this.userRepository.save(userEntity);
        UserDTO dto = new UserDTO(updatedEntity.getId(), updatedEntity.getName(), updatedEntity.getEmail());
        return dto;
    }

    public UserDTO partialUpdate(Long id, UserDTO userDTO){
        Optional<UserEntity> existingEntityOpt = this.userRepository.findById(id); 
        UserEntity existingEntity = existingEntityOpt.orElseThrow(() -> new NotFoundException("User not found"));

        UserEntity toUpdate = new UserEntity(
            id,
            Optional.ofNullable(userDTO.getName()).orElse(existingEntity.getName()),
            Optional.ofNullable(userDTO.getEmail()).orElse(existingEntity.getEmail())
        );
        UserEntity updated = this.userRepository.save(toUpdate);
        UserDTO data = new UserDTO(updated.getId(), updated.getName(), updated.getEmail());
        return data;
    }

    public void delete(Long id){
        Optional<UserEntity> existingEntityOpt = this.userRepository.findById(id); 
        existingEntityOpt.orElseThrow(() -> new NotFoundException("User not found"));
        this.userRepository.deleteById(id);
    }


}
