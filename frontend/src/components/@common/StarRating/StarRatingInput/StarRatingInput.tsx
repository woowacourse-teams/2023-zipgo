import { css, styled } from 'styled-components';

import EmptyStarIcon from '@/assets/svg/empty_star_icon.svg';
import FilledStarIcon from '@/assets/svg/filled_star_icon.svg';

interface StarRatingInputProps {
  rating: number;
  onClickStar: (selectedRating: number) => void;
  size: 'small' | 'medium' | 'large' | 'extra-large';
}

const StarRatingInput = (starRatingInputProps: StarRatingInputProps) => {
  const { rating, onClickStar, size = 'medium' } = starRatingInputProps;

  const renderStars = () =>
    Array.from({ length: 5 }, (_, index) => index + 1).map(starIndex => (
      <StarLabel key={starIndex} htmlFor={`star-input-${starIndex}`}>
        <StarInput
          type="radio"
          id={`star-input-${starIndex}`}
          name="star-rating"
          value={starIndex}
          onClick={() => onClickStar(starIndex)}
        />
        <Star
          key={starIndex}
          src={starIndex <= rating ? FilledStarIcon : EmptyStarIcon}
          alt={starIndex <= rating ? `${starIndex}번째 색칠된 별` : `${starIndex}번째 빈 별`}
          size={size}
        />
      </StarLabel>
    ));

  return <StarRatingContainer>{renderStars()}</StarRatingContainer>;
};

export default StarRatingInput;

const StarRatingContainer = styled.fieldset`
  display: flex;
  gap: 0.8rem;

  margin: 0;
  padding: 0;

  border: 0;
`;

const StarInput = styled.input`
  appearance: none;
`;

const StarLabel = styled.label`
  all: unset;

  cursor: pointer;
`;

const Star = styled.img<{ size: 'small' | 'medium' | 'large' | 'extra-large' }>`
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

    if (size === 'extra-large') {
      return css`
        width: 3.6rem;
        height: 3.6rem;
      `;
    }

    return css`
      width: 2rem;
      height: 2rem;
    `;
  }}
`;
