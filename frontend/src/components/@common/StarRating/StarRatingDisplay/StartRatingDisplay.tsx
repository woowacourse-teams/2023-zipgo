import { css, styled } from 'styled-components';

import EmptyStarIcon from '@/assets/svg/empty_star_icon.svg';
import FilledStarIcon from '@/assets/svg/filled_star_icon.svg';

interface StarRatingDisplayProps {
  rating: number;
  size?: 'small' | 'medium' | 'large';
  displayRating?: boolean;
}

const StarRatingDisplay = (starRatingDisplayProps: StarRatingDisplayProps) => {
  const { rating, size = 'medium', displayRating = false } = starRatingDisplayProps;

  return (
    <StartWrapper>
      <StarContainer>
        {Array.from({ length: 5 }, (_, index) => {
          const imageUrl = index < Math.floor(rating) ? FilledStarIcon : EmptyStarIcon;
          return <Star size={size} key={index} src={imageUrl} alt={`만족도 ${rating}점`} />;
        })}
      </StarContainer>
      {displayRating && <StarRating>{rating}</StarRating>}
    </StartWrapper>
  );
};

const StartWrapper = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;
`;

const StarContainer = styled.div`
  display: flex;
  gap: 0.3rem;
  align-items: center;
`;

const Star = styled.img<{ size: 'small' | 'medium' | 'large' }>`
  ${({ size }) => {
    if (size === 'small') {
      return css`
        width: 1.6rem;
        height: 1.6rem;
      `;
    }

    if (size === 'large') {
      return css`
        width: 2.4rem;
        height: 2.4rem;
      `;
    }

    return css`
      width: 2rem;
      height: 2rem;
    `;
  }}
`;

const StarRating = styled.h3`
  margin-top: 0.3rem;

  font-size: 1.7rem;
  font-weight: 700;
  line-height: 1.7rem;
  color: ${({ theme }) => theme.color.grey250};
  letter-spacing: -0.05rem;
`;

export default StarRatingDisplay;
