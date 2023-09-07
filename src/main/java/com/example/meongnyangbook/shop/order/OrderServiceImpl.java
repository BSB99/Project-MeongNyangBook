package com.example.meongnyangbook.shop.order;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.shop.basket.Basket;
import com.example.meongnyangbook.shop.basket.repository.BasketRepository;
import com.example.meongnyangbook.shop.order.dto.OrderListResponseDto;
import com.example.meongnyangbook.shop.order.dto.OrderRequestDto;
import com.example.meongnyangbook.shop.order.dto.OrderResponseDto;
import com.example.meongnyangbook.user.User;
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
        long totalPrice = 0L;

        List<Basket> basketList = basketRepository.findByUser(user);

        for (Basket basket : basketList) {
            OrderItem orderItem = new OrderItem(basket.getCnt(), basket.getItem(), order, basket);
            if(basket.getItem().getQuantity() < basket.getCnt()) {
                throw new IllegalArgumentException(basket.getItem().getName() + "의 수량이 부족합니다.");
            }
            basket.getItem().setQuantity(basket.getItem().getQuantity()-basket.getCnt());

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
    public OrderListResponseDto getOrderList(User user) {
        List<OrderResponseDto> order = orderRepository.findAllByUser(user).stream().map(OrderResponseDto::new).toList();

        return new OrderListResponseDto(order);
    }

    @Override
    public OrderResponseDto getSingleOrderList(User user, Long orderNo) {
        Order order = orderRepository.findByUserAndId(user, orderNo);

        return new OrderResponseDto(order);
    }
}
