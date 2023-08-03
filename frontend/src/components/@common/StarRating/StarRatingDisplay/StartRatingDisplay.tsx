import { css, styled } from 'styled-components';

import EmptyStarIcon from '@/assets/svg/empty_star_icon.svg';
import FilledStarIcon from '@/assets/svg/filled_star_icon.svg';

interface StarRatingDisplayProps {
  rating: number;
  size?: 'small' | 'medium' | 'large';
}

const StarRatingDisplay = (starRatingDisplayProps: StarRatingDisplayProps) => {
  const { rating, size = 'medium' } = starRatingDisplayProps;

  return (
    <StarContainer aria-label={`만족도 ${rating}점`}>
      {Array.from({ length: 5 }, (_, index) => {
        const imageUrl = index < Math.floor(rating) ? FilledStarIcon : EmptyStarIcon;
        return <Star size={size} key={index} src={imageUrl} alt="" />;
      })}
    </StarContainer>
  );
};

const StarContainer = styled.div`
  display: flex;
  gap: 0.4rem;
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

export default StarRatingDisplay;
