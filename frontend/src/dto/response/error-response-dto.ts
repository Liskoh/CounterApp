export interface ErrorResponseDto {
    message: string;
    errors: Map<string, string>;
}