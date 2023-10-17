package ru.pnzgu.springblog.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.user.ChangeRoleRequest;
import ru.pnzgu.springblog.dto.user.ChangeUserPasswordRequest;
import ru.pnzgu.springblog.dto.user.GetUserResponse;
import ru.pnzgu.springblog.exceptions.AccessDeniedException;
import ru.pnzgu.springblog.exceptions.EntityNotFoundException;
import ru.pnzgu.springblog.exceptions.ValidationException;
import ru.pnzgu.springblog.helpers.AuthFacade;
import ru.pnzgu.springblog.helpers.ImageUtils;
import ru.pnzgu.springblog.helpers.PageDtoMaker;
import ru.pnzgu.springblog.models.Image;
import ru.pnzgu.springblog.models.UserEntity;
import ru.pnzgu.springblog.repositories.ImageRepository;
import ru.pnzgu.springblog.repositories.RoleRepository;
import ru.pnzgu.springblog.repositories.UserRepository;
import ru.pnzgu.springblog.services.UserService;

import java.io.IOException;

/**
 * Класс сервиса пользователей.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PageDtoMaker<UserEntity, GetUserResponse> pageDtoMaker;
    private final AuthFacade authFacade;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ImageUtils imageUtils;
    private final ImageRepository imageRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PageDtoMaker<UserEntity, GetUserResponse> pageDtoMaker,
                           AuthFacade authFacade, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ImageUtils imageUtils, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.pageDtoMaker = pageDtoMaker;
        this.authFacade = authFacade;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.imageUtils = imageUtils;
        this.imageRepository = imageRepository;
    }

    /**
     * Получает пользователя, отправившего запрос. 
     *
     * @return пользователя
     */
    @Override
    public GetUserResponse getCurrentUser() {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return mapToResponse(user);
    }

    /**
     * Получает аватар пользователя, отправившего запрос.
     *
     * @return аватар пользователя
     */
    @Override
    @Transactional
    public byte[] getCurrentUserAvatar() {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var avatar = user.getAvatar();
        
        return imageUtils.decompress(avatar.getData());
    }

    /**
     * Получает пользователя по указанному идентификатору. 
     *
     * @param id идентификатор пользователя 
     * @return пользователя с указанным идентификатором 
     */
    @Override
    public GetUserResponse getById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        return mapToResponse(user);
    }

    /**
     * Получает всех пользователей.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @return объект PageDto, содержащий пользователей
     */
    @Override
    public PageDto<GetUserResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = userRepository.findAll(pageRequest);
        
        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает всех пользователей, отфильтрованных по идентификатору роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @param roleId     идентификатор роли, которую должен иметь пользователь
     * @return объект PageDto, содержащий пользователей
     */
    @Override
    public PageDto<GetUserResponse> getAllByRoleId(int pageNumber, int pageSize, int roleId) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = userRepository.findAllByRole_Id(roleId, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает всех пользователей, отфильтрованных по названию роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @param name       название роли, которую должен иметь пользователь
     * @return объект PageDto, содержащий пользователей
     */
    @Override
    public PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = userRepository.findAllByRole_Name(name, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Меняет пароль пользователя. 
     *
     * @param request объект запроса на смену пароля
     * @return пользователя
     */
    @Override
    public GetUserResponse changePassword(ChangeUserPasswordRequest request) {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new ValidationException("Wrong password");
        
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword()))
            throw new ValidationException("Password must be new");
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        
        var updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    /**
     * Меняет роль пользователя. 
     *
     * @param id      идентификатор пользователя
     * @param request объект запроса на смену роли
     * @return обновленного пользователя
     */
    @Override
    public GetUserResponse changeRole(int id, ChangeRoleRequest request) {
        var authorities = authFacade.getAuth().getAuthorities();
        
        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN")))
            throw new AccessDeniedException("Access denied");
        
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        
        user.setRole(role);

        var updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    /**
     * Меняет аватар пользователя.
     *
     * @param avatar новый аватар пользователя
     * @return обновленного пользователя
     */
    @Override
    @Transactional
    public GetUserResponse changeAvatar(MultipartFile avatar) throws IOException {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        imageRepository.delete(user.getAvatar());
        
        var image = new Image(avatar.getOriginalFilename(), avatar.getContentType(), imageUtils.compress(avatar.getBytes()));
        user.setAvatar(image);
        
        var updatedUser = userRepository.save(user);
        
        return mapToResponse(user);
    }

    /**
     * Удаляет пользователя по указанному идентификатору. 
     *
     * @param id идентификатор пользователя
     */
    @Override
    public void delete(int id) {
        var authorities = authFacade.getAuth().getAuthorities();
        var username = authFacade.getAuth().getName();
        var currentUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN")) && currentUser.getId() != id)
            throw new AccessDeniedException("Access denied");
        
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    /**
     * Преобразует UserEntity в GetUserResponse.
     *
     * @param user объект UserEntity, который будет преобразован
     * @return объект GetUserResponse, содержащий данные из объекта UserEntity
     */
    private GetUserResponse mapToResponse(UserEntity user) {
        return new GetUserResponse(user.getId(), user.getRole().getId(), user.getUsername(), user.getFirstName(),
                user.getLastName(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
