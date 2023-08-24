package com.example.meongnyangbook.shop.order.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.basket.entity.Basket;
import com.example.meongnyangbook.shop.basket.repository.BasketRepository;
import com.example.meongnyangbook.shop.order.dto.OrderRequestDto;
import com.example.meongnyangbook.shop.order.dto.OrderResponseDto;
import com.example.meongnyangbook.shop.order.entity.Order;
import com.example.meongnyangbook.shop.order.entity.OrderItem;
import com.example.meongnyangbook.shop.order.entity.OrderStatusEnum;
import com.example.meongnyangbook.shop.order.repository.OrderItemRepository;
import com.example.meongnyangbook.shop.order.repository.OrderRepository;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.dto.OrderUserResponseDto;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BasketRepository basketRepository;

    @Override
    @Transactional
    public ApiResponseDto createOrder(User user, OrderRequestDto requestDto) {
        Order order = new Order(requestDto, user);
        Long totalPrice = 0L;

        List<Basket> basketList = basketRepository.findByUser(user);

        for (Basket basket : basketList) {
            OrderItem orderItem = new OrderItem(basket.getCnt(), basket.getItem(), order, basket);

            orderItemRepository.save(orderItem);
            totalPrice += basket.getCnt() * basket.getItem().getPrice();
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return new ApiResponseDto("주문 성공", 201);
    }

    @Override
    @Transactional
    public ApiResponseDto updateOrder(User user) {
        Order order = orderRepository.findByUserAndStatusEnum(user, OrderStatusEnum.ORDER_IN_PROGRESS);

        order.setStatusEnum();

        return new ApiResponseDto("주문 완료", 200);
    }

    @Override
    public OrderResponseDto getOrderList(User user) {
        List<Order> order = orderRepository.findAllByUserAndStatusEnum(user, OrderStatusEnum.ORDER_COMPLETED);

        return new OrderResponseDto(order);
    };
}
