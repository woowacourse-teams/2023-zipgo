import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

import BackIcon from '@/assets/svg/back_btn.svg';
import StarRatingInput from '@/components/@common/StarRating/StarRatingInput/StarRatingInput';
import Template from '@/components/@common/Template';
import { AdverseReaction, StoolCondition, TastePreference } from '@/types/review/client';

interface LocationState {
  state: {
    isEditMode: boolean;
    userRating: number;
    reviewDetail: {
      reviewId: number;
      tastePreference: TastePreference;
      stoolCondition: StoolCondition;
      adverseReactions: AdverseReaction[];
      comment: string;
    };
  };
}

const ReviewStarRating = () => {
  const navigate = useNavigate();
  const location = useLocation() as LocationState;
  const { isEditMode = false, userRating = 0, reviewDetail } = { ...location.state };

  const { petFoodId, name, imageUrl } = {
    // 식품상세 api 연동 전 임시 데이터..
    petFoodId: 1,
    name: '퓨리나 프로플랜 민감한 장건강 12kg',
    imageUrl: 'https://www.purinapetcare.co.kr/data/files/e3469d6a8936e6416891b97bdbd7c708.png',
  };

  const onClickStar = (selectedRating: number) => {
    navigate(`/pet-foods/${petFoodId}/reviews/write/${selectedRating}/detail/${isEditMode}`, {
      state: { petFoodId, name, imageUrl, reviewDetail },
    });
  };

  const goBack = () => navigate(-1);

  return (
    <Template.WithoutHeader footer={false}>
      <Container>
        <BackButton type="button" aria-label="뒤로가기" onClick={goBack}>
          <img src={BackIcon} alt="" />
        </BackButton>
        <FoodImageWrapper>
          <FoodImage src={imageUrl} alt={`${name}`} />
        </FoodImageWrapper>
        <FoodName>{name}</FoodName>
        <Label>이 사료 어땠어요?</Label>
        <StarRatingInput rating={userRating} onClickStar={onClickStar} size="extra-large" />
        <Caption>별점을 매겨주세요!</Caption>
      </Container>
    </Template.WithoutHeader>
  );
};

export default ReviewStarRating;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100vw;
  height: 100vh;
`;

const BackButton = styled.button`
  all: unset;

  cursor: pointer;

  position: fixed;
  top: 6.8rem;
  left: 2rem;
`;

const FoodImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
`;

const FoodImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 18rem;
  height: 18rem;
  margin-bottom: 1.6rem;

  border: 1px solid ${({ theme }) => theme.color.grey200};
  border-radius: 20px;
  box-shadow: 0 4px 16px rgb(0 0 0 / 2%);
`;

const FoodName = styled.p`
  overflow: hidden;
  display: -webkit-box;

  width: 18rem;
  margin: 1.6rem 0;
  margin-bottom: 4.4rem;

  font-size: 1.4rem;
  font-weight: 600;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey400};
  text-overflow: ellipsis;
  letter-spacing: -0.5px;
  word-break: break-word;

  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
`;

const Label = styled.p`
  margin-bottom: 1.6rem;

  font-size: 2.8rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.black};
  letter-spacing: -0.5px;
`;

const Caption = styled.p`
  margin-top: 0.8rem;

  font-size: 1.4rem;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.grey250};
`;
